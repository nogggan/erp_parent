package com.entor.erp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entor.erp.entity.Emp;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IEmpService;
import com.entor.erp.vo.EmpVo;

@Controller
@RequestMapping("/sys")
public class SysController {
	
	@Autowired
	private IEmpService empService;
	
	@PostMapping("/login")
	@ResponseBody
	public Result<Emp> login(@Valid EmpVo empVo,HttpSession session){
		String id = session.getId();
		System.out.println("SessionId:"+id);
		Emp emp = new Emp();
		try {
			BeanUtils.copyProperties(emp, empVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Emp realEmp = empService.getEmp(emp);
		session.setAttribute("emp", realEmp);
		return Result.success(realEmp);
	}
	
	@GetMapping("/info")
	@ResponseBody
	public Result<Emp> getSessionEmp(HttpSession session){
		Object attribute = session.getAttribute("emp");
		if(attribute==null)
			throw new GlobalException(Result.error(ResultType.USER_NO_EXISTS, "请先登录"));
		return Result.success((Emp)attribute);
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login.html";
	}
	
}
