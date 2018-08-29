package com.entor.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.entity.Orders;
import com.entor.erp.entity.StoreWarn;
import com.entor.erp.service.IStoreWarnService;

@RestController
@RequestMapping("/warn")
public class StoreWarnController {
	
	@Autowired
	private IStoreWarnService warnService;

	@PostMapping("/page")
	public ResponseEntity<Map<String, Object>> getPage(StoreWarn storeWarn,@RequestParam(value="page",defaultValue="1")String pageNow,
			@RequestParam(value="rows",defaultValue="3")String pageSize){
		Integer realPageNow = 1;
		Integer realPageSize = 3;
		try {
			realPageNow = Integer.parseInt(pageNow);
		} catch (Exception e) {}
		try {
			realPageSize = Integer.parseInt(pageSize);
		} catch (Exception e) {}
		Page<StoreWarn> page = new Page<>(realPageNow, realPageSize);
		page = warnService.getPage(page, storeWarn);
		Map<String, Object> body = new HashMap<>();
		body.put("total", page.getTotal());
		body.put("rows",page.getRecords());
		return new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
	}
	
}
