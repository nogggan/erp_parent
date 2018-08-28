package com.entor.erp.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.Emp;
import com.entor.erp.entity.Orders;
import com.entor.erp.entity.OrdersDetail;

public interface IOrdersService extends IService<Orders>{
	
	Page<Orders> getPage(Page<Orders> page,Orders orders);
	
	boolean addOrderAndOrderDetail(Orders orders,List<OrdersDetail> ordersDetails);
	
	//修改商品的审核状态为已审核
	boolean check(Long uuid,Emp emp);
	
	//修改商品的审核状态为已确认
	boolean confirm(Long uuid,Emp emp);
	
	//商品入库
	boolean instore(Long storeUuid,Long orderDetailUuid,Long empUuid);
	
	//商品出库
	boolean outstore(Long storeUuid,Long orderDetailUuid,Long empUuid);
	
}
