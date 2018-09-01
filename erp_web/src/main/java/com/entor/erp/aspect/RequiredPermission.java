package com.entor.erp.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.entor.erp.interceptor.AuthHandlerInterceptor;

/**
 * 权限拦截注解  {@link PermissionAspectJ} 提供支持，该注解会校检登录状态
 * 
 * {@link AuthHandlerInterceptor} 提供基本权限过滤拦截，而此注解提供细粒度的权限控制
 * 
 * @author Gan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {
	
	/**
	 *	指定权限点的名称,可以为多个，默认是And（与操作）
	 * @return
	 */
	String[] value();
	
	boolean isAnd() default true;

}
