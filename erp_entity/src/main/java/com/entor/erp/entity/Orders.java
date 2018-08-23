package com.entor.erp.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.entor.erp.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

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
	
	private String type;
	
	private Emp creater;
	
	private Emp checker;
	
	private Emp starter;
	
	private Emp ender;
	
	private Long supplieruuid;
	
	private Double totalmoney;
	
	private String state;

}
