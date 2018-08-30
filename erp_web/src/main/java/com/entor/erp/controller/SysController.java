package com.entor.erp.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entor.erp.annotation.NeedLogin;
import com.entor.erp.entity.Emp;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IEmpService;
import com.entor.erp.service.impl.EmpServiceImpl;
import com.entor.erp.vo.EmpVo;

@Controller
@RequestMapping("/sys")
public class SysController {
	
	@Autowired
	private IEmpService empService;
	
	@PostMapping("/login")
	public Result<String> login(@Valid EmpVo empVo,HttpServletResponse response){
		Emp emp = new Emp();
		try {
			BeanUtils.copyProperties(emp, empVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		empService.login(response,emp);
		return Result.success("登录成功");
	}
	
	@GetMapping("/info")
	public Result<Emp> getSessionEmp(Emp emp){
		if(emp==null)
			throw new GlobalException(Result.error(ResultType.USER_NO_EXISTS, "请先登录"));
		return Result.success(emp);
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletResponse response,@CookieValue(value=EmpServiceImpl.USERNAME_TOKEN,required=false) String cookieToken) {
		Cookie cookie = new Cookie(EmpServiceImpl.USERNAME_TOKEN, "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		if(!StringUtils.isEmpty(cookieToken))
			empService.removeEmpByToken(cookieToken);
		return "redirect:/login.html";
	}
	
	@GetMapping("/test")
	@ResponseBody
	@NeedLogin
	public Emp get(Emp emp) {
		return emp;
	}
	
}
