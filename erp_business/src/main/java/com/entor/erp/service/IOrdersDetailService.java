package com.entor.erp.service;

import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.OrdersDetail;

public interface IOrdersDetailService extends IService<OrdersDetail>{
	
	boolean isExistsNotInstoreByOrderId(Long ordersId);

}
