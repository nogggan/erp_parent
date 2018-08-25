package com.entor.erp.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class OrdersDetailVo {

	private Long uuid;
	
	@NotNull(message="商品编号不能为空")
	private Long goodsuuid;
	
	@NotBlank(message="商品名字不能为空")
	private String goodsname;
	
	@NotNull(message="价钱不能为空")
	private Double price;
	
	@Size(min=1,message="系统检测到商品数量异常")
	private Integer num;
	
	@NotNull(message="金额不能为空")
	private Double money;
	
	private Date endtime;
	
	private Integer ender;
	
	private Integer storeuuid;
	
	/**
	 * 0 ： 未入库
	 * 1 ： 已入库
	 */
	private String state;
	
	private Long ordersuuid;
}
