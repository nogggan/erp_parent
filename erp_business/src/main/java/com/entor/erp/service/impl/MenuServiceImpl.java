package com.entor.erp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.MenuMapper;
import com.entor.erp.entity.Menu;
import com.entor.erp.service.IMenuService;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService{

	@Override
	public List<Menu> getMenu(Menu menu) {
		EntityWrapper<Menu> wrapper = new EntityWrapper<>(menu);
		List<Menu> menus = selectList(wrapper);
		List<Menu> linkMenus = new ArrayList<>();
		menus.stream().forEach(m->{
			String parentId = m.getMenuid();
			if(!StringUtils.isEmpty(parentId)) {
				List<Menu> childMenus = getMenuByParentId(parentId); 
				m.setMenus(childMenus);
			}
			linkMenus.add(m);
		});
		return linkMenus;
	}

	@Override
	public List<Menu> getMenuByParentId(String menuId) {
		EntityWrapper<Menu> entityWrapper = new EntityWrapper<>();
		entityWrapper.eq("pid", menuId);
		return selectList(entityWrapper);
	}

}
