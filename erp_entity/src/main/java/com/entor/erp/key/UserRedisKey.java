package com.entor.erp.key;

import org.gan.spring.boot.autoconfigure.redis.AbstractRedisKey;

public class UserRedisKey extends AbstractRedisKey{

	public UserRedisKey() {
		super();
	}

	public UserRedisKey(int expire, String prefix) {
		super(expire, prefix);
	}
	
	
	

}
