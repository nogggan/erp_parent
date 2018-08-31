package com.entor.erp.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;
import org.springframework.web.servlet.HandlerInterceptor;

import com.entor.erp.context.EmpRequestContext;
import com.entor.erp.entity.Emp;
import com.entor.erp.entity.Menu;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IMenuService;

import lombok.extern.slf4j.Slf4j;

/**
 * 权限拦截器
 * @author Gan
 *
 */
@Component
@Slf4j
public class AuthHandlerInterceptor implements HandlerInterceptor{
	
	@Autowired
	private IMenuService menuService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取所有的一级菜单
		Menu menuCriteria = new Menu();
		menuCriteria.setPid("0");
		List<Menu> firstMenu = menuService.getMenu(menuCriteria);
		List<Menu> allMenus = new ArrayList<>();
		firstMenu.stream().forEach(fm->{
			List<Menu> menus = fm.getMenus();
			if(menus != null && !menus.isEmpty())
				allMenus.addAll(menus);
		});
		//获取请求的路径
		final String REQUEST_URI = request.getRequestURI();
		log.debug(String.format("正在拦截URI：%s", REQUEST_URI));
		//该请求路径是否需要权限
		boolean isRequiredPrem = allMenus.stream().anyMatch(menu->{
			String url = menu.getUrl();
			url = url.substring(0,url.indexOf("?")==-1?url.length():url.indexOf("?"));
			return url.equals(REQUEST_URI);
		});
		if(isRequiredPrem) {
			Emp emp = EmpRequestContext.get();
			if(emp==null)
				throw new GlobalException(Result.error(ResultType.PERMISSION_DENIED, "权限不足，请先登录!"));
			List<Menu> empFirstMenu = menuService.getMenuByEmpId(emp.getUuid());
			List<Menu> empMenus = new ArrayList<>();
			empFirstMenu.stream().forEach(efm->{
				List<Menu> list = efm.getMenus();
				if(list!=null&&!list.isEmpty()) {
					empMenus.addAll(list);
				}
			});
			boolean isOwnPerm = empMenus.stream().anyMatch(menu->{
				String url = menu.getUrl();
				url = url.substring(0,url.indexOf("?")==-1?url.length():url.indexOf("?"));
				return url.equals(REQUEST_URI);
			});
			log.debug(String.format("需要拥有权限，是否拥有权限:%s", isOwnPerm));
			if(!isOwnPerm)
				throw new GlobalException(Result.error(ResultType.PERMISSION_DENIED, "权限不足"));
		}
		return true;
	}

}
