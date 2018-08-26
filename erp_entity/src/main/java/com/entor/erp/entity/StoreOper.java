package com.entor.erp.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 库存操作记录表
 * @author Gan
 *
 */
@Data
@TableName(value="STOREOPER",resultMap="storeOperMap")
@KeySequence(value="STOREOPER_SEQ")
public class StoreOper {

	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	@TableField(el="emp.uuid",value="empuuid")
	private Emp emp;
	
	private Date opertime;
	
	@TableField(el="store.uuid",value="storeuuid")
	private Store store;
	
	@TableField(el="goods.uuid",value="goodsuuid")
	private Goods goods;
	
	private Integer num;
	
	/**
	 * 1：入库
	 * 2：出库
	 */
	private String type;

	
}
