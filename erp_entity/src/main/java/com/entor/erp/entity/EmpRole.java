package com.entor.erp.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@Data
@TableName(value="EMP_ROLE")
public class EmpRole {

	private Long empid;
	
	private Long roleid;
	
}
