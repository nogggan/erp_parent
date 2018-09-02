package com.entor.erp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.entor.erp.constant.Constant;
import com.entor.erp.context.EmpRequestContext;
import com.entor.erp.entity.Emp;
import com.entor.erp.service.IEmpService;
import com.entor.erp.service.impl.EmpServiceImpl;

@Component
public class ResolveTokenHandlerInterceptor implements HandlerInterceptor{
	
	@Autowired
	private IEmpService empService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		String token = getCookieValue(request,EmpServiceImpl.USERNAME_TOKEN);
//		Emp emp = StringUtils.isEmpty(token)?null:empService.getEmpByToken(token);
		HttpSession session = request.getSession();
		Emp emp = (Emp) session.getAttribute(Constant.USER_NAME);
		EmpRequestContext.set(emp);
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		EmpRequestContext.clear();
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
