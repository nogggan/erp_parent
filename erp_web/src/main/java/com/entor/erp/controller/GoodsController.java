package com.entor.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.aspect.RequiredPermission;
import com.entor.erp.entity.Goods;
import com.entor.erp.service.IGoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private IGoodsService goodsService;

	@GetMapping("/{id}")
	@ResponseBody
	public Goods get(@PathVariable("id")Long id) {
		return goodsService.selectById(id);
	}
	
	@PostMapping("/all")
	@ResponseBody
	public List<Goods> getAll(){
		return goodsService.selectList(null);
	}
	
	@PostMapping("/page")
	@RequiredPermission("商品")
	public ResponseEntity<Map<String, Object>> getPage(Goods goods,@RequestParam(value="page",defaultValue="1")String pageNow,
			@RequestParam(value="rows",defaultValue="3")String pageSize){
		Integer realPageNow = 1;
		Integer realPageSize = 3;
		try {
			realPageNow = Integer.parseInt(pageNow);
		} catch (Exception e) {}
		try {
			realPageSize = Integer.parseInt(pageSize);
		} catch (Exception e) {}
		Page<Goods> page = new Page<>(realPageNow, realPageSize);
		page = goodsService.getPage(page, goods);
		Map<String, Object> body = new HashMap<>();
		body.put("total", page.getTotal());
		body.put("rows",page.getRecords());
		return new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
	}
	
}
