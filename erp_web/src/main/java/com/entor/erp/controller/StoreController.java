package com.entor.erp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entor.erp.entity.Store;
import com.entor.erp.service.IStoreService;

@RestController
@RequestMapping("/store")
public class StoreController {
	
	@Autowired
	private IStoreService storeService;
	
	@PostMapping("/all")
	public List<Store> list(){
		return storeService.findListFilterPwd();
	}

}
