package com.entor.erp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.annotation.NeedLogin;
import com.entor.erp.aspect.RequiredPermission;
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
import com.entor.erp.vo.WebModel;
import com.entor.erp.web.socket.WebSocketService;

@Controller
@RequestMapping("/orders")
@Validated
public class OrdersController {

	@Autowired
	private IOrdersService orderService;
	
	@Autowired
	private WebSocketService webSocketService;
	
	@PostMapping("/page")
	@RequiredPermission(value= {"销售订单查询","订单查询"},isAnd=false)
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
	@RequiredPermission("订单审核")
	public Result<String> check(@RequestParam(value="uuid",required=false) 
				@Validated @NotNull(message="商品uuid不能为空")Long id, Emp emp){
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
	@RequiredPermission("订单确认")
	public Result<String> confirm(@RequestParam(value="uuid",required=false)
				@Validated @NotNull(message="商品uuid不能为空")Long id,Emp emp){
		if(orderService.confirm(id,emp))
			return Result.success("确认成功");
		return Result.error(ResultType.ERROR, "确认失败");
	}
	
	/**
	 * 采购申请或者销售入库
	 * @param supplierVo 供应商
	 * @param data 商品详细来源
	 * @param session
	 * @return
	 */
	@PostMapping("/add")
	@RequiredPermission(value= {"采购申请","销售订单录入"},isAnd=false)
	public Result<String> add(@Valid SupplierVo supplierVo,@RequestParam(value="json",required=false)String data,Emp emp){
		if(StringUtils.isEmpty(data))
			throw new GlobalException(Result.error(ResultType.ARGUMENT_NOT_MATCH, "系统检测到商品数据为空"));
		Supplier supplier = new Supplier();
		try {
			BeanUtils.copyProperties(supplier, supplierVo);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Orders orders = new Orders();
		orders.setSupplier(supplier);
		orders.setCreatetime(new Date());
		orders.setCreater(emp);
		orders.setTotalmoney(0.0)	;	//初始化总金额
		orders.setState("0");			//未审核
		orders.setType(supplier.getType());	//采购订单或者销售订单
		List<OrdersDetailVo> ordersDetails = null;
		try {
			ordersDetails = JSONObject.parseArray(data, OrdersDetailVo.class);
		} catch (Exception e2) {
			throw new GlobalException(Result.error(ResultType.ARGUMENT_NOT_MATCH, "系统检测到商品数据为空"));
		}
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
		if(orderService.addOrderAndOrderDetail(orders, details)) {
			//推送消息
			if(supplierVo.getType().equals("1"))
				webSocketService.send(new WebModel().setMsg("有新的采购订单").setUrl("/order/check.html?type=1").setTitle("采购审核").setCode(0));
			else
				webSocketService.send(new WebModel().setMsg("有新的销售入库订单").setUrl("/order/outstore.html?type=2").setTitle("销售订单出库").setCode(0));
			return supplierVo.getType().equals("1")?Result.success("采购成功"):Result.success("销售订单录入成功");
		}
		else
			return supplierVo.getType().equals("1")?Result.error(ResultType.ORDERS_ERROR, "采购失败"):Result.error(ResultType.ORDERS_ERROR, "销售订单录入失败");
	}
	
	/**
	 * 订单入库
	 * @param storeUuid 仓库编号
	 * @param orderDetailUuid	订单详细编号
	 * @param session
	 * @return
	 */
	@PostMapping("/instore")
	@RequiredPermission("订单入库")
	public Result<String> instore(@RequestParam(value="storeUuid",required=false) @Validated @NotNull(message="仓库编号不能为空") Long storeUuid,
							@RequestParam(value="orderDetailUuid",required=false) @Validated @NotNull(message="订单详细编号不能为空") Long orderDetailUuid,
							Emp emp){
		if(orderService.instore(storeUuid, orderDetailUuid, emp.getUuid()))
			return Result.success("入库成功");
		return Result.error(ResultType.ORDERS_ERROR, "入库失败");
	}
	
	
	/**
	 * 订单出库
	 * @param storeUuid 仓库编号
	 * @param orderDetailUuid 订单详细编号
	 * @param session
	 * @return
	 */
	@PostMapping("/outstore")
	@RequiredPermission("销售订单出库")
	public Result<String> outstore(@RequestParam(value="storeUuid",required=false) @Validated @NotNull(message="仓库编号不能为空") Long storeUuid,
							@RequestParam(value="orderDetailUuid",required=false) @Validated @NotNull(message="订单详细编号不能为空") Long orderDetailUuid,
							Emp emp){
		if(orderService.outstore(storeUuid, orderDetailUuid, emp.getUuid()))
			return Result.success("销售订单出库成功");
		return Result.error(ResultType.ERROR, "销售订单出库失败，请重新尝试");
	}
	
}
