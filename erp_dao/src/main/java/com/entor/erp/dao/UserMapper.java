package com.entor.erp.dao;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entor.erp.entity.User;

@Repository
public interface UserMapper extends BaseMapper<User>{

	User getById(Integer id);
	
}
