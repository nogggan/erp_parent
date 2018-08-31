package com.entor.erp.aspect;

public @interface RequiredPermission {
	
	/**
	 *	指定权限的名称,可以为多个，是AND的关系
	 * @return
	 */
	String[] value();

}
