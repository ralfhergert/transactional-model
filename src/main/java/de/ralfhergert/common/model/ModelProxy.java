package de.ralfhergert.common.model;

import de.ralfhergert.common.model.archtype.Model;
import de.ralfhergert.common.model.context.Context;
import de.ralfhergert.common.model.event.ModelChangeEvent;
import de.ralfhergert.common.model.event.ModelChangeEventListener;
import de.ralfhergert.common.model.meta.MethodInfo;
import de.ralfhergert.common.model.meta.ModelClassInfo;
import de.ralfhergert.common.model.property.ListPropertyHolder;
import de.ralfhergert.common.model.property.PropertyHolder;
import de.ralfhergert.common.model.property.PropertyHolderFactory;
import de.ralfhergert.common.model.property.ValuePropertyHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelProxy implements Model, InvocationHandler {

	private static final Set<Method> supportedDelegationMethods = new HashSet<>(Stream.of(Model.class, Object.class)
		.flatMap(clazz -> Stream.of(clazz.getMethods()))
		.collect(Collectors.toList())
	);

	private final List<ModelChangeEventListener> listeners = new ArrayList<>();

	private final Map<Method,MethodInfo> methodPropertyLookupMap = new HashMap<>();

	private final Map<String,PropertyHolder> properties = new HashMap<>();

	private Context context = null;

	/**
	 * This constructor will analyze the given typeClass
	 */
	public ModelProxy(ModelClassInfo modelClassInfo) {
		if (modelClassInfo == null) {
			throw new IllegalArgumentException("modelClassInfo must not be null");
		}
		methodPropertyLookupMap.putAll(modelClassInfo.getPropertyMethods());
		// create all necessary propertyHandlers.
		for (Map.Entry<String,Type> propertyEntry : modelClassInfo.getPropertyTypes().entrySet()) {
			properties.put(propertyEntry.getKey(), PropertyHolderFactory.createPropertyFor(propertyEntry.getKey(), propertyEntry.getValue()));
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (supportedDelegationMethods.contains(method)) {
			return method.invoke(this, args);
		}
		if (methodPropertyLookupMap.containsKey(method)) {
			final MethodInfo methodInfo = methodPropertyLookupMap.get(method);
			final PropertyHolder propertyHolder = properties.get(methodInfo.getPropertyName());
			if (methodInfo.getMethodType() == MethodInfo.MethodType.SETTER && propertyHolder instanceof ValuePropertyHolder) {
				((ValuePropertyHolder)propertyHolder).setValue(args[0], getContext());
				return null;
			} else if (methodInfo.getMethodType() == MethodInfo.MethodType.GETTER && propertyHolder instanceof ValuePropertyHolder) {
				return ((ValuePropertyHolder)propertyHolder).getValue();
			} else if (methodInfo.getMethodType() == MethodInfo.MethodType.ADD && propertyHolder instanceof ListPropertyHolder) {
				return ((ListPropertyHolder)propertyHolder).addElement(args[0], getContext());
			} else if (methodInfo.getMethodType() == MethodInfo.MethodType.REMOVE && propertyHolder instanceof ListPropertyHolder) {
				return ((ListPropertyHolder)propertyHolder).removeElement(args[0], getContext());
			}
		}
		throw new IllegalArgumentException("method '" + method + "' could not be invoked");
	}

	private Context getContext() {
		if (context != null) { // this will be the case when a transactional context is given
			return context;
		} else { // create a inline context on the fly.
			return new Context() {
				@Override
				public void pushChangeEvent(ModelChangeEvent... events) {
					listeners.forEach(listener -> listener.process(events));
				}
			};
		}
	}

	public Map<String, PropertyHolder> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	@Override
	public void attachListener(ModelChangeEventListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void detachListener(ModelChangeEventListener listener) {
		listeners.remove(listener);
	}
}
