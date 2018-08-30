package com.entor.erp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entor.erp.entity.Role;

@Repository
public interface RoleMapper extends BaseMapper<Role>{
	
	List<Role> getRoleByEmpId(Long empid);

}
