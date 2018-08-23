package com.entor.erp.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpLogInterceptor  implements HandlerInterceptor{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map<String, String[]> parameterMap = request.getParameterMap();
		String method = request.getMethod();
		String requestURI = request.getRequestURI();
		String startTime = "1";
		return true;
	}

}
