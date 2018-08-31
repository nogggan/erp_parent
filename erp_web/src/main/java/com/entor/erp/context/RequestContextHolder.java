package com.entor.erp.context;

import javax.servlet.http.HttpServletRequest;

public class RequestContextHolder {
	
private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<>();
	
	public static final void set(HttpServletRequest request) {
		REQUEST.set(request);
	}
	
	public static final HttpServletRequest get() {
		return REQUEST.get();
	}
	
	public static final void clear() {
		REQUEST.remove();
	}

}
