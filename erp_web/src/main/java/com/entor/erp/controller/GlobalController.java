package com.entor.erp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {

	@GetMapping(path="/login.html")
	public String toLogin(HttpSession session) {
		if(session.getAttribute("emp")!=null)
			return "redirect:/index.html";
		return "login";
	}
	
	@GetMapping(path="/index.html")
	public String toIndex(HttpSession session) {
		if(session.getAttribute("emp")==null)
			return "redirect:/login.html";
		return "index";
	}
	
	@GetMapping(path="/order/add.html")
	public String toOrdersAdd(HttpSession session) {
		if(session.getAttribute("emp")==null)
			return "redirect:/login.html";
		return "orders/add";
	}
	
}