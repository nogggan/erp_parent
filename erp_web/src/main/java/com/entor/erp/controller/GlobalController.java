package com.entor.erp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.entor.erp.entity.Emp;

@Controller
public class GlobalController {

	/*
	 * 	registry.addViewController("/list").setViewName("dep/list");
registry.addViewController("/orders").setViewName("orders/list");
registry.addViewController("/goods").setViewName("goods/list");
	 */

	@GetMapping(path="/list")
	public String toDeptList(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "dep/list";
	}
	
	@GetMapping(path="/orders")
	public String toOrders(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "orders/list";
	}
	
	@GetMapping(path="/goods")
	public String toGoods(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "goods/list";
	}

	@GetMapping(path="/login.html")
	public String toLogin(Emp emp) {
		if(emp != null)
			return "redirect:/index.html";
		return "login";
	}
	
	@GetMapping(path= {"/","/index.html"})
	public String toIndex(Emp emp) {
		if(emp == null)
			return "login";
		return "index";
	}
	
	@GetMapping(path="/order/add.html")
	public String toOrdersAdd(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "orders/add";
	}
	
	@GetMapping(path="/order/check.html")
	public String toOrdersCheck(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "orders/check";
	}
	
	@GetMapping(path="/order/confirm.html")
	public String toOrdersConfirm(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "orders/confirm";
	}
	
	@GetMapping(path="/order/instore.html")
	public String toOrdersInstore(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "orders/instore";
	}
	
	@GetMapping(path="/order/outstore.html")
	public String toOrdersOutstore(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "orders/outstore";
	}
	
	@GetMapping(path="/order/report.html")
	public String toGoodsCount(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "orders/report";
	}
	
	@GetMapping(path="/storewarn/storewarn.html")
	public String toStoreWarn(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "storewarn/storewarn";
	}
	
	@GetMapping(path="/menu/tree.html")
	public String toTree(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "tree/tree";
	}
	
	@GetMapping(path="/emp/userAuth.html")
	public String toUserAuth(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "tree/userAuth";
	}
	
	@GetMapping(path="/emp/list.html")
	public String toEmpList(Emp emp) {
		if(emp == null)
			return "redirect:/login.html";
		return "emp/list";
	}
	
}
