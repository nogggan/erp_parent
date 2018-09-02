package com.entor.erp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 仓库表
 * @author Gan
 *
 */
@Data
@TableName(resultMap="storeMap")
@KeySequence(value="STROE_SEQ")
public class Store implements Serializable{
	
	private static final long serialVersionUID = -469526883849361633L;

	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	private String name;
	
	@TableField(el="emp.uuid",value="empuuid")
	private Emp emp;

}
