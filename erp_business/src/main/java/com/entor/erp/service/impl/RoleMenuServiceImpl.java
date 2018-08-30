package com.entor.erp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.RoleMenuMapper;
import com.entor.erp.entity.RoleMenu;
import com.entor.erp.entity.Tree;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IRoleMenuService;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
						implements IRoleMenuService{

	/**
	 * 修改角色权限
	 */
	@Transactional
	@Override
	public boolean updateRoleMenu(Long roleid, String[] menuIds) {
		Long[] ids = new Long[menuIds.length];
		for(int i=0;i<menuIds.length;i++) {
			try {
				Long id = Long.parseLong(menuIds[i]);
				ids[i] = id;
			} catch (Exception e) {
				throw new GlobalException(Result.error(ResultType.FORMAT_ERROR, "菜单编号不合法"));
			}
		}
		boolean deleteRowData = delete(new EntityWrapper<RoleMenu>().eq("roleid", roleid));
		if(deleteRowData) {
			List<RoleMenu> roleMenus = new ArrayList<>();
			Stream.of(ids).forEach(i->{
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleid(roleid);
				roleMenu.setMenuid(i);
				roleMenus.add(roleMenu);
			});
			if(insertBatch(roleMenus,menuIds.length))
				return true;
			else
				throw new GlobalException(Result.error(ResultType.ERROR, "修改权限失败，请重新尝试"));
		}
		return false;
	}

}
