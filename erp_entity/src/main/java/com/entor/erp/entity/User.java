package com.entor.erp.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户表
 * @author Gan
 *
 */
@Data
public class User implements Serializable{
	private static final long serialVersionUID = 3687838467707737991L;

	private String name;
	
	private Integer id;
	
}
