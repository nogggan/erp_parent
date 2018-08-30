package com.entor.erp.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

//create table ROLE_MENU(roleid number(11),menuid number(11),primary key(roleid,menuid));
@Data
public class Role {
	
	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	private String name;

}
