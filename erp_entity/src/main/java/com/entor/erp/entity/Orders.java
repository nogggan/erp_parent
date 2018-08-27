package com.entor.erp.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.entor.erp.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/*
 * 订单表
 */
@Data
@KeySequence(value="ORDERS_SEQ")
@TableName(resultMap="ordersMap")
public class Orders {
	
	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	@JsonSerialize(using=DateSerializer.class)
	private Date createtime;
	
	@JsonSerialize(using=DateSerializer.class)
	private Date checktime;
	
	@JsonSerialize(using=DateSerializer.class)
	private Date starttime;
	
	@JsonSerialize(using=DateSerializer.class)
	private Date endtime;
	
	/**
	 * 1：采购订单
	 * 2：销售订单
	 */
	private String type;
	
	@TableField(el="creater.uuid")
	private Emp creater;
	
	@TableField(el="checker.uuid")
	private Emp checker;
	
	@TableField(el="starter.uuid")
	private Emp starter;
	
	@TableField(el="ender.uuid")
	private Emp ender;
	
	@TableField(el="supplier.uuid",value="supplieruuid")
	private Supplier supplier;
	
	private Double totalmoney;
	
	/**采购订单
	 * 0: 未审核
	 * 1:已审核
	 * 2:已确认
	 * 3:已结束
	 * 
	 * 销售订单
	 * 0：未入库
	 * 1：已入库
	 */
	private String state;

}
