package com.entor.erp.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 供应商表
 * @author Gan
 *
 */
@Data
public class Supplier {
	
	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	private String name;

	private String address;
	
	private String contact;
	
	private String tele;
	
	private String email;
	
	private String type;
	
}
