package com.entor.erp.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.entity.Emp;
import com.entor.erp.entity.Orders;
import com.entor.erp.entity.OrdersDetail;
import com.entor.erp.entity.Supplier;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IOrdersDetailService;
import com.entor.erp.service.IOrdersService;
import com.entor.erp.vo.SupplierVo;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private IOrdersService orderService;
	
	@GetMapping("/{id}")
	public Orders get(@PathVariable("id") Long id) {
		return orderService.selectById(id);
	}
	
	@PostMapping("/page")
	public ResponseEntity<Map<String, Object>> getPage(Orders orders,@RequestParam(value="page",defaultValue="1")String pageNow,
			@RequestParam(value="rows",defaultValue="3")String pageSize){
		Integer realPageNow = 1;
		Integer realPageSize = 3;
		try {
			realPageNow = Integer.parseInt(pageNow);
		} catch (Exception e) {}
		try {
			realPageSize = Integer.parseInt(pageSize);
		} catch (Exception e) {}
		Page<Orders> page = new Page<>(realPageNow, realPageSize);
		page = orderService.getPage(page, orders);
		Map<String, Object> body = new HashMap<>();
		body.put("total", page.getTotal());
		body.put("rows",page.getRecords());
		return new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public Result<String> add(@Valid SupplierVo supplierVo,@RequestParam("json")String data,HttpSession session){
		Supplier supplier = new Supplier();
		try {
			BeanUtils.copyProperties(supplier, supplierVo);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Emp emp = (Emp) session.getAttribute("emp");
		Orders orders = new Orders();
		orders.setSupplier(supplier);
		orders.setCreatetime(new Date());
		orders.setStarter(emp);
		orders.setTotalmoney(0.0);//初始化总金额
		orders.setState("0");//未审核
		orders.setType("1");//采购订单
		List<OrdersDetail> ordersDetails = JSONObject.parseArray(data, OrdersDetail.class);
		if(ordersDetails == null || ordersDetails.isEmpty())
			throw new GlobalException(Result.error(ResultType.ARGUMENT_NOT_MATCH, "系统检测到商品数据为空"));
		if(orderService.addOrderAndOrderDetail(orders, ordersDetails))
			return Result.success("处理成功");
		else
			return Result.error(ResultType.ORDERS_ORDERS_ERROR, "采购失败");
	}
	
}
