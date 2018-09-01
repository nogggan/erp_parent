package com.entor.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entor.erp.aspect.RequiredPermission;
import com.entor.erp.entity.OrdersDetail;
import com.entor.erp.service.IOrdersDetailService;

@Controller
@RequestMapping("/orderdetail")
public class OrdersDetailController {
	
	@Autowired
	private IOrdersDetailService orderDetailService;
	
	@GetMapping("/{id}")
	@RequiredPermission(value= {"订单审核","订单确认","订单入库","订单查询","销售订单出库","销售订单查询"},isAnd=false)
	public ResponseEntity<Map<String,Object>> get(@PathVariable("id")Long id) {
		Map<String, Object> map = new HashMap<>();
		List<OrdersDetail> orderDetail = orderDetailService.selectList(new EntityWrapper<OrdersDetail>().eq("ordersuuid", id));
		map.put("total", orderDetail.size());
		map.put("rows", orderDetail);
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
}
