package com.entor.erp.resolver;

import static org.assertj.core.api.Assertions.contentOf;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.entor.erp.entity.Emp;
import com.entor.erp.service.IEmpService;
import com.entor.erp.service.impl.EmpServiceImpl;

@Component
public class EmpHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{
	
	@Autowired
	private IEmpService empService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType()==Emp.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Method method = parameter.getMethod();
		Class<?> declaringClass = method.getDeclaringClass();
		String controller = declaringClass.getName();
		System.out.println(controller);
		System.out.println(method.getName());
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String cookieValue = getCookieValue(request,EmpServiceImpl.USERNAME_TOKEN);
		String paramValue = request.getParameter(EmpServiceImpl.USERNAME_TOKEN);
		String token = paramValue==null?cookieValue:paramValue;
		if(StringUtils.isEmpty(token))
			return null;
		return empService.getEmpByToken(token);
	}

	private String getCookieValue(HttpServletRequest request, String usernameToken) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if(usernameToken.equals(name))
					return cookie.getValue();
			}
		}
		return null;
	}


}
