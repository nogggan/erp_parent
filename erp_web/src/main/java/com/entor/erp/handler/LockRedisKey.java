package com.entor.erp.handler;

import org.gan.spring.boot.autoconfigure.redis.AbstractRedisKey;

public class LockRedisKey extends AbstractRedisKey{

	public LockRedisKey() {
		super();
	}

	public LockRedisKey(String prefix) {
		super(0, prefix);
	}
	
	public LockRedisKey(int expire, String prefix) {
		super(expire, prefix);
	}
	
	public static final LockRedisKey LOCK = new LockRedisKey("store_warn_lock");

}
