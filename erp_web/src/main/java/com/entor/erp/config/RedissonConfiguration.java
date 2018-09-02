package com.entor.erp.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfiguration {

	@Autowired
	private RedisProperties redisProperties;
	
	@Bean(destroyMethod="shutdown")
	public RedissonClient redissonClient() {
		Config config = new Config();
		String host = redisProperties.getHost();
		int port = redisProperties.getPort();
		config.useSingleServer().setAddress(new StringBuilder().append("http://").append(host)
				.append(":").append(port).toString());
		return Redisson.create(config);
	}
	
}
