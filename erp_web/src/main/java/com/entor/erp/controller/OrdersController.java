package com.entor.erp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.entor.erp.service.IOrdersService;
import com.entor.erp.util.ValidatorUtils;
import com.entor.erp.vo.OrdersDetailVo;
import com.entor.erp.vo.SupplierVo;

@RestController
@RequestMapping("/orders")
@Validated
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
	
	/**
	 * 修改订单状态为已审核
	 * @param id 修改订单的uuid
	 * @return
	 */
	@PostMapping("/check")
	public Result<String> check(@RequestParam(value="uuid",required=false) 
				@Validated @NotNull(message="商品uuid不能为空")Long id,HttpSession session){
		Emp emp = (Emp) session.getAttribute("emp");
		if(orderService.check(id,emp))
			return Result.success("审核成功");
		return Result.error(ResultType.ERROR, "审核失败");
	}
	
	/**
	 * 修改订单状态为已确认
	 * @param id 修改订单的uuid
	 * @return
	 */
	@PostMapping("/confirm")
	public Result<String> confirm(@RequestParam(value="uuid",required=false) 
				@Validated @NotNull(message="商品uuid不能为空")Long id,HttpSession session){
		Emp emp = (Emp) session.getAttribute("emp");
		if(orderService.confirm(id,emp))
			return Result.success("确认成功");
		return Result.error(ResultType.ERROR, "确认失败");
	}
	
	/**
	 * 采购申请
	 * @param supplierVo 供应商
	 * @param data 商品详细来源
	 * @param session
	 * @return
	 */
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
		orders.setStarttime(new Date());
		orders.setStarter(emp);
		orders.setTotalmoney(0.0);//初始化总金额
		orders.setState("0");//未审核
		orders.setType("1");//采购订单
		List<OrdersDetailVo> ordersDetails = JSONObject.parseArray(data, OrdersDetailVo.class);
		if(ordersDetails == null || ordersDetails.isEmpty())
			throw new GlobalException(Result.error(ResultType.ARGUMENT_NOT_MATCH, "系统检测到商品数据为空"));
		ValidatorUtils.validatorList(ordersDetails);
		List<OrdersDetail> details = new ArrayList<>();
		ordersDetails.forEach(x->{
			OrdersDetail ordersDetail = new OrdersDetail();
			try {
				BeanUtils.copyProperties(ordersDetail, x);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			details.add(ordersDetail);
		});
		if(orderService.addOrderAndOrderDetail(orders, details))
			return Result.success("处理成功");
		else
			return Result.error(ResultType.ORDERS_ERROR, "采购失败");
	}
	
	@PostMapping("/instore")
	public Result<String> instore(@RequestParam(value="storeUuid",required=false) @Validated @NotNull(message="仓库编号不能为空") Long storeUuid,
							@RequestParam(value="orderDetailUuid",required=false) @Validated @NotNull(message="商品详细编号不能为空") Long orderDetailUuid,
							HttpSession session){
		Emp emp = (Emp) session.getAttribute("emp");
		if(orderService.instore(storeUuid, orderDetailUuid, emp.getUuid()))
			return Result.success("入库成功");
		return Result.error(ResultType.ORDERS_ERROR, "入库失败");
	}
	
}
