package com.entor.erp.entity;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

@Data
@KeySequence(value="DEP_SEQ")
public class Dep {
	
	@TableId
	private Long  uuid;
	
	private String name;
	
	private String tele;

}
