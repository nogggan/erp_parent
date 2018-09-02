package com.entor.erp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

//create table ROLE_MENU(roleid number(11),menuid number(11),primary key(roleid,menuid));
//create table EMP_ROLE(empid number(11),roleid number(11),primary key(empid,roleid));
@Data
public class Role implements Serializable{
	
	private static final long serialVersionUID = -5156158317923810298L;

	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	private String name;
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj)
			return true;
		if(obj instanceof Role) {
			Role role = (Role) obj;
			if(role.getName().equals(this.getName()))
				return true;
		}
		return false;
	}

}
