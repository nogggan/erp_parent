package com.entor.erp.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.gan.spring.boot.autoconfigure.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.EmpMapper;
import com.entor.erp.entity.Emp;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.key.UserRedisKey;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IEmpService;
import com.entor.erp.util.UUIDUtils;

@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService{
	
	public static final String USERNAME_TOKEN = "_token";
	
	@Autowired
	private RedisService redisService;

	@Override
	public void login(HttpServletResponse response,Emp emp) {
		Emp dbEmp = getByUserName(emp.getUsername());
		if(dbEmp==null || (!dbEmp.getPassword().equals(emp.getPassword())))
			throw new GlobalException(Result.error(ResultType.USER_NO_EXISTS, "用户名或密码错误"));
		String token = UUIDUtils.uuid();
		addCookie(response,token);
		//1800秒有效期
		redisService.set(UserRedisKey.LOGIN_TOKEN, token, dbEmp);
	}


	private void addCookie(HttpServletResponse response, String token) {
		Cookie cookie = new Cookie(USERNAME_TOKEN, token);
		cookie.setPath("/");
		response.addCookie(cookie);
	}


	@Override
	public Emp getByUserName(String username) {
		if(StringUtils.isEmpty(username))
			return null;
		Emp dbEmp = redisService.get(UserRedisKey.LOGIN_EMP, username, Emp.class);
		if(dbEmp != null) {
			return dbEmp;
		}else {
			dbEmp = selectOne(new EntityWrapper<Emp>().eq("username", username));
			if(dbEmp != null)
				redisService.set(UserRedisKey.LOGIN_EMP, username, dbEmp);
			return dbEmp;
		}
	}


	@Override
	public Emp getEmpByToken(String token) {
		if(StringUtils.isEmpty(token))
			return null;
		Emp emp = redisService.get(UserRedisKey.LOGIN_TOKEN, token, Emp.class);
		if(emp!=null) emp.setPassword("******");
		return emp;
	}


	@Override
	public void removeEmpByToken(String token) {
		redisService.del(UserRedisKey.LOGIN_TOKEN, token);
	}


}
