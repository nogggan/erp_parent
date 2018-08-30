package com.entor.erp.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.entity.Emp;
import com.entor.erp.service.IEmpService;
import com.entor.erp.vo.EmpCriteria;

@Controller
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	private IEmpService empService;
	
	@PostMapping("/page")
	public ResponseEntity<Map<String, Object>> getPage(EmpCriteria empCriteria,@RequestParam(value="page",defaultValue="1")String pageNow,
			@RequestParam(value="rows",defaultValue="3")String pageSize){
		Integer realPageNow = 1;
		Integer realPageSize = 3;
		try {
			realPageNow = Integer.parseInt(pageNow);
		} catch (Exception e) {}
		try {
			realPageSize = Integer.parseInt(pageSize);
		} catch (Exception e) {}
		Emp emp = new Emp();
		try {
			BeanUtils.copyProperties(emp, empCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Page<Emp> page = new Page<>(realPageNow, realPageSize);
		page = empService.getPage(page, emp);
		Map<String, Object> body = new HashMap<>();
		body.put("total", page.getTotal());
		body.put("rows",page.getRecords());
		return new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
	}
	
}
