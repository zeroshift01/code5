package com.code5.fw.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zero
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceAnnotation {

	String errJspKey() default "err";

	boolean isLogin() default true;

	String auth() default "";

	boolean isInternal() default false;

	String checkMethod() default "";
}
