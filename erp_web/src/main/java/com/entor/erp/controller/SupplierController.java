package com.entor.erp.controller;

import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entor.erp.entity.Supplier;
import com.entor.erp.service.ISupplierService;

@RestController
@RequestMapping("/supplier")
@Validated
public class SupplierController {

	@Autowired
	private ISupplierService supplierService;
	
	@GetMapping("/{id}")
	public Supplier get(@PathVariable("id")Long id) {
		return supplierService.selectById(id);
	}
	
	@PostMapping("/all")
	public List<Supplier> getAll(@RequestParam(value="q",required=false) String name,
			@Validated @NotBlank(message="供应商或客户类型不能为空")@RequestParam(value="type",required=false)String type){
		EntityWrapper<Supplier> wrapper = new EntityWrapper<>();
		wrapper.eq("type", type);
		if(!StringUtils.isEmpty(name))
			wrapper.like("name", name);
		return supplierService.selectList(wrapper);
	}
	
}
