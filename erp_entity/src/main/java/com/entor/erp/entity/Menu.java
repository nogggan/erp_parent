package com.entor.erp.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

@Data
public class Menu {

	@TableId
	private String menuid;
	
	private String menuname;
	
	private String icon;
	
	private String url;
	
	private String pid;
	
	@TableField(exist=false)
	private List<Menu> menus;
	
}
