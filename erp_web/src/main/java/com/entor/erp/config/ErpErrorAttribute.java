package com.entor.erp.config;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

public class ErpErrorAttribute extends DefaultErrorAttributes{

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		Map<String, Object> errorAttributes = new LinkedHashMap<>();
		errorAttributes.put("timestamp", new Date());
		RequestAttributes requestAttributes = webRequest;
		Integer status = (Integer)requestAttributes
				.getAttribute("javax.servlet.error.status_code", RequestAttributes.SCOPE_REQUEST);
		if(status != null)
			errorAttributes.put("code", status);
		else
			errorAttributes.put("code", 999);
		String path = getAttribute(requestAttributes, "javax.servlet.error.request_uri");
		errorAttributes.put("path", path);
		String msg = getAttribute(requestAttributes, "javax.servlet.error.message");
		Throwable exception = getAttribute(requestAttributes, DefaultErrorAttributes.class.getName()+".ERROR");
		if(exception == null) 
			exception = getAttribute(requestAttributes, "javax.servlet.error.exception");
		if(exception instanceof ServletException && exception.getCause() != null) {
			exception = ((ServletException) exception).getCause();
		}
		if(StringUtils.isEmpty(msg)) {
			if(exception != null)
				msg = exception.getMessage();
		}
		if(StringUtils.isEmpty(msg))
			errorAttributes.put("msg", "No message available");
		errorAttributes.put("msg", msg);
		return errorAttributes;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
		return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}
	
}
