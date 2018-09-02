package com.entor.erp.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.entor.erp.annotation.NeedLogin;
import com.entor.erp.constant.Constant;
import com.entor.erp.entity.Emp;
import com.entor.erp.result.Result;
import com.entor.erp.service.IEmpService;
import com.entor.erp.service.impl.EmpServiceImpl;
import com.entor.erp.vo.EmpVo;

@Controller
@RequestMapping("/sys")
public class SysController {
	
	@Autowired
	private IEmpService empService;
	
	@PostMapping("/login")
	public Result<String> login(@Valid EmpVo empVo,HttpSession session){
		Emp emp = new Emp();
		try {
			BeanUtils.copyProperties(emp, empVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		empService.login(response,emp);
		Emp dbEmp = empService.login(emp);
		session.setAttribute(Constant.USER_NAME, dbEmp);
		return Result.success("登录成功");
	}
	
	/**
	 * 获取登录用户信息
	 * @param emp
	 * @return
	 */
	@GetMapping("/info")
	public Result<Emp> getSessionEmp(@NeedLogin Emp emp){
		return Result.success(emp);
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session,@CookieValue(value=EmpServiceImpl.USERNAME_TOKEN,required=false) String cookieToken) {
//		Cookie cookie = new Cookie(EmpServiceImpl.USERNAME_TOKEN, "");
//		cookie.setMaxAge(0);
//		response.addCookie(cookie);
//		if(!StringUtils.isEmpty(cookieToken))
//			empService.removeEmpByToken(cookieToken);
		session.removeAttribute(Constant.USER_NAME);
		return "redirect:/login.html";
	}
	
}
