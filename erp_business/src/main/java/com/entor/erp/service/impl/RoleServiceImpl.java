package com.entor.erp.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.RoleMapper;
import com.entor.erp.entity.Role;
import com.entor.erp.service.IRoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
						implements IRoleService{
	

	@Override
	public Page<Role> getPage(Page<Role> page, Role role) {
		EntityWrapper<Role> wrapper = new EntityWrapper<>(role);
		return selectPage(page, wrapper);
	}

}
