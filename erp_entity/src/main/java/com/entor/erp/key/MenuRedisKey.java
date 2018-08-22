package com.entor.erp.key;

import org.gan.spring.boot.autoconfigure.redis.AbstractRedisKey;

public class MenuRedisKey extends AbstractRedisKey{

	public MenuRedisKey() {
		super();
	}
	
	public MenuRedisKey(String prefix) {
		super(0, prefix);
	}

	public MenuRedisKey(int expire, String prefix) {
		super(expire, prefix);
	}
	
	public static final MenuRedisKey MENU = new MenuRedisKey("menu");

}
