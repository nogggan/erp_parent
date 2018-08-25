package com.entor.erp.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.Orders;
import com.entor.erp.entity.OrdersDetail;

public interface IOrdersService extends IService<Orders>{
	
	Page<Orders> getPage(Page<Orders> page,Orders orders);
	
	boolean addOrderAndOrderDetail(Orders orders,List<OrdersDetail> ordersDetails);

}
