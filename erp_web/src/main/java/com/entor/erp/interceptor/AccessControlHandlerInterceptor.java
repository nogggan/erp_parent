package com.entor.erp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.entor.erp.annotation.NeedLogin;
import com.entor.erp.context.EmpRequestContext;
import com.entor.erp.entity.Emp;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;

public class AccessControlHandlerInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			NeedLogin methodNeedLogin = handlerMethod.getMethodAnnotation(NeedLogin.class);
			Class<? extends Object> handleClass = handlerMethod.getBean().getClass();
			Emp emp = EmpRequestContext.get();
			if(emp == null) {
				if(methodNeedLogin != null)
					throw new GlobalException(Result.error(ResultType.NOT_LOGIN,"请先登录"));
				if(handleClass.isAnnotationPresent(NeedLogin.class))
					throw new GlobalException(Result.error(ResultType.NOT_LOGIN,"请先登录"));
			}
		}
		return true;
	}

}
