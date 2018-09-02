package com.entor.erp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * create view v_storewarn as
 * select g.uuid,g.name,s.inNum storeNum,o.outNum outNum from goods g,(select goodsuuid,sum(num) inNum from storedetail group by goodsuuid) s,(select ot.goodsuuid,sum(ot.num) outNum from orders o,orderdetail ot where o.uuid = ot.ordersuuid and o.type=2 and o.state=0 group by ot.goodsuuid) o
where g.uuid=s.goodsuuid and s.goodsuuid=o.goodsuuid and s.inNum < o.outNum;
 * @author gan
 *
 */
@Data
@TableName(value="v_storewarn")
public class StoreWarn implements Serializable{

	private static final long serialVersionUID = 5166993026948612689L;

	@TableId
	private Long uuid;
	
	private String name;
	
	private Integer storenum;
	
	private Integer outnum;
	
}
