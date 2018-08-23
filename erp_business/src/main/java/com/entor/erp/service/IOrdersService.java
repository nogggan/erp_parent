package com.entor.erp.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.Orders;

public interface IOrdersService extends IService<Orders>{
	
	Page<Orders> getPage(Page<Orders> page,Orders orders);

}
