package com.entor.erp.entity;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

@Data
@KeySequence(value="GOODS_SEQ")
public class Goods {

	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	private String name;
	
	private String origin;
	
	private String producer;
	
	private String unit;
	
	private Long inprice;
	
	private Long outprice;
	
	private Long goodstypeuuid;
	
}
