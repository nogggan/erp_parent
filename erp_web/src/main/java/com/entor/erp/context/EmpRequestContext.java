package com.entor.erp.context;

import com.entor.erp.entity.Emp;

public class EmpRequestContext {
	
	private static final ThreadLocal<Emp> EMP = new ThreadLocal<>();
	
	public static final void set(Emp emp) {
		EMP.set(emp);
	}
	
	public static final Emp get() {
		return EMP.get();
	}
	
	public static final void clear() {
		EMP.remove();
	}

}
