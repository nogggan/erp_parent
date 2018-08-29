package com.entor.erp.key;

import org.gan.spring.boot.autoconfigure.redis.AbstractRedisKey;

public class UserRedisKey extends AbstractRedisKey{

	public UserRedisKey() {
		super();
	}

	public UserRedisKey(String prefix) {
		super(0, prefix);
	}
	
	public UserRedisKey(int expire, String prefix) {
		super(expire, prefix);
	}
	
	public static final UserRedisKey LOGIN_EMP = new UserRedisKey("login_emp");
	
	public static final UserRedisKey LOGIN_TOKEN = new UserRedisKey(1800,"_token");
	
}
