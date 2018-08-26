package com.entor.erp.entity;

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
public class Store {
	
	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	private String name;
	
	@TableField(el="emp.uuid",value="empuuid")
	private Emp emp;

}
