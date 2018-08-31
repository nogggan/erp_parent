package com.entor.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.entor.erp.aspect.RequiredPermission;
import com.entor.erp.result.Result;
import com.entor.erp.vo.WebModel;
import com.entor.erp.web.socket.WebSocketService;

@Controller
@RequestMapping("/jms")
public class JmsController {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private WebSocketService webSocketService;
	
	@GetMapping("/test")
	@RequiredPermission(value= {"部门","员工","员工角色分配"})
	public Result<String> send(){ 
		jmsTemplate.convertAndSend("new-message", "下订单了");
		webSocketService.send(new WebModel().setMsg("有新的销售订单").setUrl("/order/outstore.html?type=2").setTitle("销售订单出库"));
		return Result.success("成功发送");
	}
	
	@JmsListener(destination="new-message")
	public void handle(String message) {
		System.out.println("收到了消息，当前线程"+Thread.currentThread().getName()+":"+message);
	}
	
}
