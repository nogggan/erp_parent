package com.entor.erp.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {
	
	/**
	 *	指定权限的名称,可以为多个，是AND的关系
	 * @return
	 */
	String[] value();

}
