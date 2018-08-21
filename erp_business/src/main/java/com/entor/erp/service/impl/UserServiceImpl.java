package com.entor.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entor.erp.dao.UserMapper;
import com.entor.erp.entity.User;
import com.entor.erp.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional
	public User getById(Integer id) {
		return userMapper.selectById(id);
	}

}
