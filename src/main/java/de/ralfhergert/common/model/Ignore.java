package de.ralfhergert.common.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to flag methods to be ignored when analyzing
 * classes with reflection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Ignore {

	/** This field can be used as documentation. */
	String reason();
}
