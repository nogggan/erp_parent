package com.entor.erp.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
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
@TableName(value="ORDERDETAIL",resultMap="ordersDetailMap")
@KeySequence(value="ORDERDETAIL_SEQ")
public class OrdersDetail implements Serializable{
	
	private static final long serialVersionUID = 2120112265827885483L;

	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	private Long goodsuuid;
	
	private String goodsname;
	
	private Double price;
	
	private Integer num;
	
	private Double money;
	
	@JsonSerialize(using=DateSerializer.class)
	private Date endtime;
	
	@TableField(el="ender.uuid",value="ender")
	private Emp ender;
	
	@TableField(el="store.uuid",value="storeuuid")
	private Store store;
	
	/**采购
	 * 0 ： 未入库
	 * 1 ： 已入库
	 * 
	 * 销售
	 * 0：未出库
	 * 1：已出库
	 */
	private String state;
	
	private Long ordersuuid;


}
