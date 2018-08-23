package com.entor.erp.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.OrdersMapper;
import com.entor.erp.entity.Orders;
import com.entor.erp.service.IOrdersService;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
				implements IOrdersService{

	@Override
	public Page<Orders> getPage(Page<Orders> page, Orders orders) {
		Page<Orders> selectPage = selectPage(page, new EntityWrapper<Orders>(orders));
		return selectPage;
	}

}
