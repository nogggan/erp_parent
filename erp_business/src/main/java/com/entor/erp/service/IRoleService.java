package com.entor.erp.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.Role;

public interface IRoleService extends IService<Role>{
	
	Page<Role> getPage(Page<Role> page,Role role);

}
