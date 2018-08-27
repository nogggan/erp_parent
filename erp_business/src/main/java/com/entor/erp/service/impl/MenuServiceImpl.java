package com.entor.erp.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gan.spring.boot.autoconfigure.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.MenuMapper;
import com.entor.erp.entity.Menu;
import com.entor.erp.key.MenuRedisKey;
import com.entor.erp.service.IMenuService;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService{
	
	@Autowired
	private RedisService redisService;

	@Override
	@SuppressWarnings("unchecked")
	public List<Menu> getMenu(Menu menu) {
		List<Menu> list = redisService.get(MenuRedisKey.MENU,menu.getPid(),List.class);
		if(list == null) {
			EntityWrapper<Menu> wrapper = new EntityWrapper<>(menu);
			wrapper.orderDesc(Arrays.asList("menuid"));
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
			redisService.set(MenuRedisKey.MENU, menu.getPid(), linkMenus);
			return linkMenus;
		}
		return list;
	}

	@Override
	public List<Menu> getMenuByParentId(String menuId) {
		EntityWrapper<Menu> entityWrapper = new EntityWrapper<>();
		entityWrapper.eq("pid", menuId);
		return selectList(entityWrapper);
	}

}
