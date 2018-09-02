package com.entor.erp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@Data
@TableName(value="EMP_ROLE")
public class EmpRole implements Serializable{

	private static final long serialVersionUID = 4004426674589162416L;

	private Long empid;
	
	private Long roleid;
	
}
