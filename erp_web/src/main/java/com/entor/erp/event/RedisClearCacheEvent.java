package com.entor.erp.event;

import org.gan.spring.boot.autoconfigure.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RedisClearCacheEvent {
	
	@Autowired
	private RedisService redisService;
	
	@EventListener(classes=RedisEvent.class)
	public void clear() {
		redisService.clear();
	}

}
