package com.entor.erp.entity;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

/**
 * 菜单表
 * @author Gan
 *
 */
@Data
public class Menu {

	@TableId
	private String menuid;
	
	private String menuname;
	
	private String icon;
	
	private String url;
	
	private String pid;
	
	@TableField(exist=false)
	private List<Menu> menus = new ArrayList<>();
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj instanceof Menu) {
			Menu m = (Menu) obj;
			if(m.getMenuid().equals(this.getMenuid()))
				return true;
		}
		return false;
	}
	
}
