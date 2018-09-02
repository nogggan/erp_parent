package com.entor.erp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品表
 * @author Gan
 *
 */
@Data
@KeySequence(value="GOODS_SEQ")
public class Goods implements Serializable{

	private static final long serialVersionUID = -4342963812480296637L;

	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	private String name;
	
	private String origin;
	
	private String producer;
	
	private String unit;
	
	private Double inprice;
	
	private Double outprice;
	
	private Long goodstypeuuid;
	
}
