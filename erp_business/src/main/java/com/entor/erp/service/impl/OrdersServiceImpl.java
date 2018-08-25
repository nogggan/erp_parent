package com.entor.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.OrdersMapper;
import com.entor.erp.entity.Orders;
import com.entor.erp.entity.OrdersDetail;
import com.entor.erp.service.IOrdersDetailService;
import com.entor.erp.service.IOrdersService;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
				implements IOrdersService{
	
	@Autowired
	private IOrdersDetailService orderDetailService;

	@Override
	public Page<Orders> getPage(Page<Orders> page, Orders orders) {
		if(StringUtils.isEmpty(orders.getState())) {
			orders.setState(null);
		}
		Page<Orders> selectPage = selectPage(page, new EntityWrapper<Orders>(orders));
		return selectPage;
	}

	@Transactional
	@Override
	public boolean addOrderAndOrderDetail(Orders orders, List<OrdersDetail> ordersDetails) {
		System.out.println(ordersDetails);
		ordersDetails.forEach(orderDetail->{
			double price = orderDetail.getPrice();
			Integer num = orderDetail.getNum();
			double money = (price*num);
			orders.setTotalmoney(orders.getTotalmoney()+money);
		});
		System.out.println(orders.getTotalmoney());
		System.out.println(orders);
		if(insert(orders)) {
			ordersDetails.forEach(o->{
				o.setState("0");
				o.setOrdersuuid(orders.getUuid());
			});
			if(orderDetailService.insertBatch(ordersDetails))
				return true;
		}
		return false;
	}
	
	public void addition(Double totalMoney,Double money) {
		totalMoney+=money;
	}

}
