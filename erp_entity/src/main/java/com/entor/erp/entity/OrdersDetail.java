package com.entor.erp.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.entor.erp.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * 订单详细表
 * @author Gan
 *
 */
@Data
@TableName(value="ORDERDETAIL")
@KeySequence(value="ORDERDETAIL_SEQ")
public class OrdersDetail {
	
	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	private Long goodsuuid;
	
	private String goodsname;
	
	private Double price;
	
	private Integer num;
	
	private Double money;
	
	@JsonSerialize(using=DateSerializer.class)
	private Date endtime;
	
	private Long ender;
	
	private Long storeuuid;
	
	/**
	 * 0 ： 未入库
	 * 1 ： 已入库
	 */
	private String state;
	
	private Long ordersuuid;


}
