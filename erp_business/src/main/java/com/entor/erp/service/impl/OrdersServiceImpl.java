package com.entor.erp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.OrdersMapper;
import com.entor.erp.entity.Emp;
import com.entor.erp.entity.Goods;
import com.entor.erp.entity.Orders;
import com.entor.erp.entity.OrdersDetail;
import com.entor.erp.entity.Store;
import com.entor.erp.entity.StoreDetail;
import com.entor.erp.entity.StoreOper;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IGoodsService;
import com.entor.erp.service.IOrdersDetailService;
import com.entor.erp.service.IOrdersService;
import com.entor.erp.service.IStoreDetailService;
import com.entor.erp.service.IStoreOperService;
import com.entor.erp.service.IStoreService;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
				implements IOrdersService{
	
	@Autowired
	private IOrdersDetailService orderDetailService;
	
	@Autowired
	private IStoreDetailService storeDetailService;
	
	@Autowired
	private IStoreService storeService;
	
	@Autowired
	private IStoreOperService storeOperService;
	
	@Autowired
	private IGoodsService goodsService;

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
		ordersDetails.forEach(x->{
			Long goodsUuid = x.getGoodsuuid();
			Goods goods = goodsService.selectById(goodsUuid);
			String type = orders.getType();
			double goodsPrice = type.equals("1")?goods.getInprice().doubleValue():goods.getOutprice().doubleValue();
			if(goodsPrice!=x.getPrice().doubleValue())
				throw new GlobalException(Result.error(ResultType.ERROR, "当前操作不合法，故此警告，如有下次，将封停1小时"));
		});
		//计算订单金额
		ordersDetails.forEach(orderDetail->{
			double price = orderDetail.getPrice();
			Integer num = orderDetail.getNum();
			double money = (price*num);
			orders.setTotalmoney(orders.getTotalmoney()+money);
		});
		if(insert(orders)) {
			ordersDetails.forEach(o->{
				o.setState("0");
				o.setOrdersuuid(orders.getUuid());
			});
			if(orderDetailService.insertBatch(ordersDetails,ordersDetails.size())) {
				return true;
			}else {
				throw new GlobalException(Result.error(ResultType.ORDERS_ERROR, "订单采购失败"));
			}
		}
		return false;
	}

	@Override
	public boolean check(Long uuid,Emp emp) {
		Orders orders = new Orders();
		orders.setUuid(uuid);
		//修改订单的审核状态为已审核
		orders.setState("1");
		orders.setChecktime(new Date());
		orders.setChecker(emp);
		return updateById(orders);
	}

	@Override
	public boolean confirm(Long uuid,Emp emp) {
		Orders orders = new Orders();
		orders.setUuid(uuid);
		//修改订单的审核状态为已确认
		orders.setState("2");
		orders.setStarter(emp);
		orders.setStarttime(new Date());
		return updateById(orders);
	}

	@Transactional
	@Override
	public boolean instore(Long storeUuid, Long orderDetailUuid,Long empUuid) {
		//根据orderDetailUuid查询对应的订单详细信息
		OrdersDetail ordersDetail = orderDetailService.selectById(orderDetailUuid);
		if(ordersDetail==null) throw new GlobalException(Result.error(ResultType.ORDERS_ERROR,String.format("入库失败，原因:%s", "订单详细记录不存在")));
		//获取商品详细的下单数量
		Integer orderDetailCount = ordersDetail.getNum();
		//根据storeUuid查询对应的仓库信息
		Store store = storeService.selectById(storeUuid);
		if(store==null) throw new GlobalException(Result.error(ResultType.ORDERS_ERROR,String.format("入库失败，原因:%s", "仓库不存在")));
		// 添加或更新仓库存储记录
		boolean storeDetailIsExists = false;
		boolean isInsertAndUpdate = false;
		StoreDetail storeDetail = null;
		storeDetail = storeDetailService.findByStoreAndGoods(storeUuid, ordersDetail.getGoodsuuid());
		if(storeDetail != null) storeDetailIsExists = true;
		Goods goods = new Goods();
		goods.setUuid(ordersDetail.getGoodsuuid());
		if(!storeDetailIsExists) {
			storeDetail =  new StoreDetail();
			storeDetail.setGoods(goods);
			storeDetail.setNum(orderDetailCount);
			storeDetail.setStore(store);
			isInsertAndUpdate = storeDetailService.insert(storeDetail);
		}else {
			storeDetail.setNum(storeDetail.getNum()+orderDetailCount);
			isInsertAndUpdate = storeDetailService.updateById(storeDetail);
		}
		if(isInsertAndUpdate) {
			Emp emp = new Emp();
			emp.setUuid(empUuid);
			StoreOper storeOper = new StoreOper();
			storeOper.setEmp(emp);
			storeOper.setGoods(goods);
			storeOper.setNum(orderDetailCount);
			storeOper.setOpertime(new Date());
			storeOper.setStore(store);
			storeOper.setType("1");//1代表入库
			//添加存储操作记录
			if(storeOperService.insert(storeOper)) {
				ordersDetail.setEnder(store.getEmp());
				ordersDetail.setEndtime(new Date());
				ordersDetail.setStore(store);
				ordersDetail.setState("1");//1代表已入库
				//修改订单详细状态为已入库
				if(orderDetailService.updateById(ordersDetail)) {
					Long ordersuuid = ordersDetail.getOrdersuuid();
					//查询该订单的所有订单详细的状态为未入库的记录
					if(!orderDetailService.isExistsNotInstoreByOrderId(ordersuuid)) {
						//如果不存在未入库订单，则设置订单状态为3(已结束)
						Orders orders = new Orders();
						orders.setUuid(ordersuuid);
						orders.setState("3");
						orders.setEnder(emp);
						orders.setEndtime(new Date());
						if(updateById(orders))
							return true;
						else
							throw new GlobalException(Result.error(ResultType.ORDERS_ERROR, "订单入库失败"));
					}else
						return true;
				}else
					throw new GlobalException(Result.error(ResultType.ORDERS_ERROR, "订单入库失败"));
			}else {
				throw new GlobalException(Result.error(ResultType.ORDERS_ERROR, "订单入库失败"));
			}
		}
		return false;
	}

	@Transactional
	@Override
	public boolean outstore(Long storeUuid, Long orderDetailUuid, Long empUuid) {
		//查询对应的订单详情信息
		OrdersDetail ordersDetail = orderDetailService.selectById(orderDetailUuid);
		if(ordersDetail==null) throw new GlobalException(Result.error(ResultType.ORDERS_ERROR,String.format("销售订单出库失败，原因:%s", "订单详细记录不存在")));
		//查询对应的仓库信息
		Store store = storeService.selectById(storeUuid);
		if(store==null) throw new GlobalException(Result.error(ResultType.ORDERS_ERROR,String.format("销售订单出库失败，原因:%s", "仓库不存在")));
		//查询仓库存储商品的信息
		StoreDetail storeDetail = storeDetailService.findByStoreAndGoods(storeUuid, ordersDetail.getGoodsuuid());
		if(storeDetail==null) throw new GlobalException(Result.error(ResultType.ORDERS_ERROR,String.format("销售订单出库失败，原因:%s商品库存不足",store.getName())));
		//减库存
		Integer dbStoreCount = storeDetail.getNum();
		Integer outStoreCount = ordersDetail.getNum();
		if(dbStoreCount < outStoreCount)
			throw new GlobalException(Result.error(ResultType.ORDERS_ERROR,String.format("销售订单出库失败，原因:%s商品库存不足",store.getName())));
		if(storeDetailService.minusStock(storeDetail, outStoreCount)) {
			//修改订单状态
			Emp emp = new Emp();
			emp.setUuid(store.getEmp().getUuid());
			ordersDetail.setEnder(emp);
			ordersDetail.setEndtime(new Date());
			ordersDetail.setStore(store);
			ordersDetail.setState("1");//修改销售订单状态为已出库
			if(orderDetailService.updateById(ordersDetail)) {
				//添加仓库操作记录
				Goods goods = new Goods();
				goods.setUuid(ordersDetail.getGoodsuuid());
				StoreOper storeOper = new StoreOper();
				storeOper.setGoods(goods);
				storeOper.setOpertime(new Date());
				storeOper.setStore(store);
				storeOper.setNum(outStoreCount);
				storeOper.setType("2");//type为2代表出库
				storeOper.setEmp(emp);
				if(storeOperService.insert(storeOper)){
					//扫描OrdersDetail对应订单的所有OrdersDetail是否全部出库，如果是设置该OrdersDetail对应的订单的状态设为已出库
					if(!orderDetailService.isExistsNotInstoreByOrderId(ordersDetail.getOrdersuuid())) {
						//如果不存在，设置订单状态为已出库
						Orders orders = new Orders();
						orders.setUuid(ordersDetail.getOrdersuuid());
						orders.setEnder(emp);
						orders.setEndtime(new Date());
						orders.setState("1");
						if(updateById(orders))
							return true;
						else
							throw new GlobalException(Result.error(ResultType.ORDERS_ERROR,String.format("销售订单出库失败，原因:%s", "更新订单状态异常，请重新尝试")));
					}else
						return true;
				}else
					throw new GlobalException(Result.error(ResultType.ORDERS_ERROR,String.format("销售订单出库失败，原因:%s", "添加库存操作异常，请重新尝试")));
			}else
				throw new GlobalException(Result.error(ResultType.ORDERS_ERROR,String.format("销售订单出库失败，原因:%s", "更新订单详细状态异常，请重新尝试")));
		}
		return false;
	}

}
