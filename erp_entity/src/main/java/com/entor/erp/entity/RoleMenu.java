package com.entor.erp.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@TableName(value="ROLE_MENU")
@Data
public class RoleMenu {
	
	private Long roleid;
	
	private Long menuid;

}
