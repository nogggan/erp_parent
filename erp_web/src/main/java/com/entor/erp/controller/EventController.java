package com.entor.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entor.erp.event.RedisEvent;
import com.entor.erp.result.Result;

@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private ApplicationEventMulticaster applicationEventMulticaster;
	
	@Autowired
	private ApplicationContext context;
	
	@GetMapping("/redis")
	public Result<String> clearRedis(){
		applicationEventMulticaster.multicastEvent(new RedisEvent(context));
		return Result.success("清除成功");
	}

}
