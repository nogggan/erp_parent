package com.entor.erp.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.entor.erp.context.EmpRequestContext;
import com.entor.erp.entity.Emp;
import com.entor.erp.entity.Menu;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IMenuService;

@Component
@Aspect
public class PermissionAspectJ {
	
	@Autowired
	private IMenuService menuService;
	
	@Pointcut("@annotation(RequiredPermission)")
	public void pointcut(){}
	
	@Before(value="pointcut()")
	public void beforeCheckPermission(JoinPoint joinPoint) {
		Class<? extends Object> clazz = joinPoint.getTarget().getClass();
		String methodName = joinPoint.getSignature().getName();
		Class<?>[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getParameterTypes();
		Method method = null;
		try {
			method = clazz.getMethod(methodName, parameterTypes);
		} catch (Exception e) {
			throw new GlobalException(Result.error(ResultType.ERROR, "服务器切面异常"));
		}
		RequiredPermission annotation = method.getAnnotation(RequiredPermission.class);
		String[] value = annotation.value();
		boolean isAnd = annotation.isAnd();
		//获取登录员工的权限点
		Emp emp = EmpRequestContext.get();
		if(emp == null)
			throw new GlobalException(Result.error(ResultType.PERMISSION_DENIED, "权限不足，请先登录!"));
		List<Menu> empFirstMenus = menuService.getMenuByEmpId(emp.getUuid());
		List<Menu> empMenus = new ArrayList<>();
		empFirstMenus.stream().forEach(efm->{
			List<Menu> menus = efm.getMenus();
			if(menus!=null)
				empMenus.addAll(menus);
		});
		boolean isOwnPerm = false;
		if(isAnd) {
			long premCount = empMenus.stream().filter(x->{
				return Stream.of(value).anyMatch(y->x.getMenuname().equals(y));
			}).count();
			isOwnPerm = premCount==value.length?true:false;
		}else {
			isOwnPerm = empMenus.stream().anyMatch(x->{
				return Stream.of(value).anyMatch(v->x.getMenuname().equals(v));
			});
		}
		if(!isOwnPerm)
			throw new GlobalException(Result.error(ResultType.PERMISSION_DENIED, "权限不足!"));
	}

}
