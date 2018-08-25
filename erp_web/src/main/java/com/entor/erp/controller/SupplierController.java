package com.entor.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entor.erp.entity.Supplier;
import com.entor.erp.service.ISupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private ISupplierService supplierService;
	
	@GetMapping("/{id}")
	public Supplier get(@PathVariable("id")Long id) {
		return supplierService.selectById(id);
	}
	
	@PostMapping("/all")
	public List<Supplier> getAll(){
		return supplierService.selectList(new EntityWrapper<Supplier>().eq("type", 1));
	}
	
}
