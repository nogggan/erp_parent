package com.entor.erp.dao;

import org.springframework.stereotype.Repository;

import com.entor.erp.entity.User;

@Repository
public interface UserMapper {

	User getById(Integer id);
	
}
