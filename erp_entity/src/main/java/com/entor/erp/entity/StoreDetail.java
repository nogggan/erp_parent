package com.entor.erp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 商品仓库库存表
 * @author Gan
 *
 */
@Data
@TableName(value="STOREDETAIL",resultMap="storeDetailMap")
@KeySequence(value="STOREDETAIL_SEQ")
public class StoreDetail implements Serializable{
	
	private static final long serialVersionUID = -2509720298594417817L;

	@TableId(type=IdType.INPUT)
	private Long uuid;
	
	@TableField(el="store.uuid",value="storeuuid")
	private Store store;
	
	@TableField(el="goods.uuid",value="goodsuuid")
	private Goods goods;
	
	private Integer num;

}
