package com.entor.erp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

/**
 * 部门表
 * @author Gan
 *
 */
@Data
@KeySequence(value="DEP_SEQ")
public class Dep implements Serializable{
	
	private static final long serialVersionUID = 7599184919925603566L;

	@TableId
	private Long  uuid;
	
	private String name;
	
	private String tele;

}
