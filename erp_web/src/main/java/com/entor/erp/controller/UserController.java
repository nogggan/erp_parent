package com.entor.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entor.erp.entity.User;
import com.entor.erp.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@GetMapping(value="/get/{id}")
	public User get(@PathVariable("id")Integer id) {
		return userService.getById(id);
	}
	
}
