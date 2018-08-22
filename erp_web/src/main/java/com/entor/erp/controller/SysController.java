package com.entor.erp.controller;

import java.lang.reflect.InvocationTargetException;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.entor.erp.entity.Emp;
import com.entor.erp.result.Result;
import com.entor.erp.service.IEmpService;
import com.entor.erp.vo.EmpVo;

@RestControllerAdvice
@RequestMapping("/sys")
public class SysController {
	
	@Autowired
	private IEmpService empService;
	
	@PostMapping("/login")
	public Result<Emp> login(@Valid EmpVo empVo){
		Emp emp = new Emp();
		try {
			BeanUtils.copyProperties(emp, empVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(emp);
		Emp realEmp = empService.getEmp(emp);
		return Result.success(realEmp);
	}

}
