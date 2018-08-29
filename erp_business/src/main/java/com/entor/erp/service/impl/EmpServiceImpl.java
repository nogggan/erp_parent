package com.entor.erp.service.impl;

import org.gan.spring.boot.autoconfigure.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.EmpMapper;
import com.entor.erp.entity.Emp;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.key.UserRedisKey;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IEmpService;

@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService{
	
	@Autowired
	private RedisService redisService;

	@Override
	public Emp getEmp(Emp emp) {
		String key = emp.getName()+"_"+emp.getPassword();
		Emp dbEmp = redisService.get(UserRedisKey.LOGIN_EMP, key, Emp.class);
		if(dbEmp!=null) {
			dbEmp.setPassword("******");
			return dbEmp;
		}
		dbEmp = baseMapper.selectOne(emp);
		if(dbEmp==null)
			throw new GlobalException(Result.error(ResultType.USER_NO_EXISTS, "用户名或密码错误"));
		redisService.set(UserRedisKey.LOGIN_EMP, key, dbEmp);
		dbEmp.setPassword("******");
		return dbEmp;
	}

}
