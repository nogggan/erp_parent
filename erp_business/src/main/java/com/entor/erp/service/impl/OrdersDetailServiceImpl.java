package com.entor.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.OrdersDetailMapper;
import com.entor.erp.entity.OrdersDetail;
import com.entor.erp.service.IOrdersDetailService;

@Service
public class OrdersDetailServiceImpl extends ServiceImpl<OrdersDetailMapper, OrdersDetail>
						implements IOrdersDetailService{

	@Override
	public boolean isExistsNotInstoreByOrderId(Long ordersId) {
		OrdersDetail ordersDetail = new OrdersDetail();
		ordersDetail.setOrdersuuid(ordersId);
		ordersDetail.setState("0");
		List<OrdersDetail> result = selectList(new EntityWrapper<OrdersDetail>(ordersDetail));
		return result!=null&&result.size()>0;
	}
	
}
