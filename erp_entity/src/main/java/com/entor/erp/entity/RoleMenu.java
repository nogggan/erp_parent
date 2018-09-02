package com.entor.erp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@TableName(value="ROLE_MENU")
@Data
public class RoleMenu implements Serializable{
	
	private static final long serialVersionUID = 3845964193312506138L;

	private Long roleid;
	
	private Long menuid;

}
