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
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.entor.erp.entity.Menu;
import com.entor.erp.entity.Tree;
import com.entor.erp.service.IMenuService;

@RestControllerAdvice
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private IMenuService menuService;

	@PostMapping("/list")
	public ResponseEntity<Map<String, Object>> list(){
		Menu menu = new Menu();
		menu.setPid("0");
		List<Menu> menus = menuService.getMenu(menu);
		Map<String, Object>body = new HashMap<>();
		body.put("menus", menus);
		return new ResponseEntity<Map<String,Object>>(body, HttpStatus.OK);
	}
	
	@PostMapping("/tree")
	public List<Tree> getTree(){
		return menuService.getMenuTree();
	}
	
}
