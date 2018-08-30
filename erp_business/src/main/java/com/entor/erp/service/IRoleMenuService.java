package com.entor.erp.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.RoleMenu;
import com.entor.erp.entity.Tree;

public interface IRoleMenuService extends IService<RoleMenu>{
	
	boolean updateRoleMenu(Long roleid,String[] menuIds);
	
}
