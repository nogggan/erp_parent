package com.entor.erp.service;

import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.RoleMenu;

public interface IRoleMenuService extends IService<RoleMenu>{
	
	boolean updateRoleMenu(Long roleid,String[] menuIds);
	
}
