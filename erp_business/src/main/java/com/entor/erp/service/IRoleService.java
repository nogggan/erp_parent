package com.entor.erp.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.Menu;
import com.entor.erp.entity.Role;

public interface IRoleService extends IService<Role>{
	
	Page<Role> getPage(Page<Role> page,Role role);
	
}
