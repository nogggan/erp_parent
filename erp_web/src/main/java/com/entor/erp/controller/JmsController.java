package com.entor.erp.controller;

import java.lang.management.ThreadMXBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entor.erp.result.Result;
import com.entor.erp.web.socket.WebSocketService;

@RestController
@RequestMapping("/jms")
public class JmsController {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private WebSocketService webSocketService;
	
	@GetMapping("/test")
	public Result<String> send(){ 
		jmsTemplate.convertAndSend("new-message", "下订单了");
		webSocketService.send("您有新的订单生成了");
		return Result.success("成功发送");
	}
	
	@JmsListener(destination="new-message")
	public void handle(String message) {
		System.out.println("收到了消息，当前线程"+Thread.currentThread().getName()+":"+message);
	}

}
