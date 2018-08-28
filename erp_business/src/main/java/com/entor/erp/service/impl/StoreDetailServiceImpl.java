package com.entor.erp.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.StoreDetailMapper;
import com.entor.erp.entity.StoreDetail;
import com.entor.erp.service.IStoreDetailService;

@Service
public class StoreDetailServiceImpl extends ServiceImpl<StoreDetailMapper, StoreDetail>
						implements IStoreDetailService{

	@Override
	public StoreDetail findByStoreAndGoods(Long storeUuid,Long goodsUuid) {
		EntityWrapper<StoreDetail> wrapper = new EntityWrapper<>();
		wrapper.eq("storeuuid", storeUuid);
		wrapper.eq("goodsuuid", goodsUuid);
		return selectOne(wrapper);
	}

	@Override
	public boolean minusStock(StoreDetail storeDetail, Integer outNum) {
		return baseMapper.minusStoreCount(storeDetail, outNum)==1;
	}

}
