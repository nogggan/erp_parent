package com.entor.erp.handler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimerHandler {
	
	@Scheduled(cron="0/30 * * * * *")
	public void handlerStoreWarn() {
		log.debug("正在处理库存警告");
	}
	
}
