package com.entor.erp.handler;
import java.util.concurrent.TimeUnit;

import org.gan.spring.boot.autoconfigure.redis.RedisService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
public class TimerHandler implements InitializingBean{
	
	private long lockTimeout = 5;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private IStoreWarnService storeWarnService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private RedissonClient redissonCLient;
	
	@Deprecated
//	@Scheduled(cron="0 */1 * * * *")
	public void handlerStoreWarn() {
		log.debug("正在处理库存警告");
		Long result = redisService.setnx(LockRedisKey.LOCK, "lock", String.valueOf(System.currentTimeMillis()+lockTimeout));
		if(result != null && result.intValue() == 1) {
			log.debug("获得分布式锁");
			checkStore();
		}else {
			String timeout = redisService.get(LockRedisKey.LOCK, "lock",String.class);
			Long lock_time_out = null;
			try {
				lock_time_out = Long.parseLong(timeout);
			} catch (Exception e) {}
			if(lock_time_out == null) lock_time_out = System.currentTimeMillis() + 5000;
			//锁存在但已经超时
			if(timeout != null && System.currentTimeMillis() > lock_time_out) {
				//设置新锁的同时，获取旧值
				String oldTimeout = redisService.getSet(LockRedisKey.LOCK, "lock", String.valueOf(System.currentTimeMillis()+lockTimeout));
				//如果锁为空，那么是安全的。如果锁是相同的，那么在此期间没有其他进程修改过，也是安全的。
				if(oldTimeout == null || (oldTimeout!=null && oldTimeout.equals(timeout))) {
					log.debug("获得分布式锁");
					checkStore();
				}else {
					log.debug("没有获得分布式锁");
				}
			}else {
				log.debug("没有获得分布式锁");
			}
		}
	}
	
	@Scheduled(cron="0 */1 * * * *")
	public void handlerWarn() {
		boolean isOwnLock = false;
		RLock lock = redissonCLient.getLock(LockRedisKey.LOCK.prefix());
		try {
			if(isOwnLock = lock.tryLock(0, lockTimeout, TimeUnit.SECONDS)) {
				log.debug("获得分布式锁：{}，当前线程: {}",LockRedisKey.LOCK.prefix(),Thread.currentThread().getName());
				checkStore();
			}else {
				log.debug("没有获得分布式锁：{}，当前线程: {}",LockRedisKey.LOCK.prefix(),Thread.currentThread().getName());
			}
		} catch (Exception e) {
			log.error("获取分布式锁发生异常");
		}finally {
			if(!isOwnLock)
				return;
			log.debug(lockTimeout+"秒后释放分布式锁：{}，当前线程: {}",LockRedisKey.LOCK.prefix(),Thread.currentThread().getName());
		}
	}
	
	public void checkStore() {
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

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			lockTimeout = Long.parseLong(environment.getProperty("lockTimeout"));
		} catch (Exception e) {
		}
	}
	
}
