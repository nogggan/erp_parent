package com.entor.erp.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpLogInterceptor  implements HandlerInterceptor{
	
	private static final String START = "request_start_time";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map<String, String[]> parameterMap = request.getParameterMap();
		String method = request.getMethod();
		String requestURI = request.getRequestURI();
		long startTime = System.currentTimeMillis();
		request.setAttribute(START, startTime);
		log.debug("《《《《《");
		log.debug(String.format("PreHandle（）方法 ： 请求方法 ： %s ,请求URI ：%s , 请求参数 ：%s",method,requestURI,
				JSONObject.toJSONString(parameterMap)));
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.debug("PostHandle （）方法执行 完成");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();
		long startTime = (long) request.getAttribute(START);
		log.debug(String.format("请求处理时间 ： %s",endTime-startTime));
		log.debug("》》》》》");
	}

}
