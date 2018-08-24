package com.entor.erp.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

public class RedisEvent extends ApplicationContextEvent{

	public RedisEvent(ApplicationContext source) {
		super(source);
	}

	private static final long serialVersionUID = 1L;

}
