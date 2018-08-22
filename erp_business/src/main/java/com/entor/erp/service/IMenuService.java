package com.entor.erp.service;

import java.util.List;

import com.entor.erp.entity.Menu;

public interface IMenuService {

	List<Menu> getMenu(Menu menu);
	
	List<Menu> getMenuByParentId(String menuId);
	
}
