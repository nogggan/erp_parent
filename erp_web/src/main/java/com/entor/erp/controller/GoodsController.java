package com.entor.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entor.erp.entity.Goods;
import com.entor.erp.service.IGoodsService;

@RestController
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private IGoodsService goodsService;

	@GetMapping("/{id}")
	public Goods get(@PathVariable("id")Long id) {
		return goodsService.selectById(id);
	}
	
	@PostMapping("/all")
	public List<Goods> getAll(){
		return goodsService.selectList(null);
	}
	
}
