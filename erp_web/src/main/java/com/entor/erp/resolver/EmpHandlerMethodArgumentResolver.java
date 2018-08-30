package com.entor.erp.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.entor.erp.annotation.NeedLogin;
import com.entor.erp.context.EmpRequestContext;
import com.entor.erp.entity.Emp;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;

public class EmpHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType()==Emp.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		NeedLogin needLogin = parameter.getParameterAnnotation(NeedLogin.class);
		Emp emp = EmpRequestContext.get();
		if(needLogin !=null && emp == null ) {
			throw new GlobalException(Result.error(ResultType.NOT_LOGIN, "请先登录"));
		}
		return emp;
	}


}
