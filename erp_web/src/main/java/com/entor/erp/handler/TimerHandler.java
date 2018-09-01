package com.entor.erp.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.entity.StoreWarn;
import com.entor.erp.service.IStoreWarnService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimerHandler {
	
	@Autowired
	private IStoreWarnService storeWarnService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Scheduled(cron="0 */1 * * * *")
	public void handlerStoreWarn() {
		log.debug("正在处理库存警告");
		Page<StoreWarn> page = new Page<>(1,3);
		page = storeWarnService.getPage(page, null);
		if(page.getTotal() > 0) {
			log.debug("发送处理库存警告的邮件");
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setSubject("库存警告");
			simpleMailMessage.setText("系统现在有"+page.getTotal()+"中商品库存紧张!");
			simpleMailMessage.setFrom("535211279@qq.com");
			simpleMailMessage.setTo("13642413572@163.com");
			javaMailSender.send(simpleMailMessage);
		}
	}
	
}
