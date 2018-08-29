package com.entor.erp.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.StoreWarnMapper;
import com.entor.erp.entity.StoreWarn;
import com.entor.erp.service.IStoreWarnService;

@Service
public class StoreWarnServiceImpl extends ServiceImpl<StoreWarnMapper, StoreWarn>
						implements IStoreWarnService{

	@Override
	public Page<StoreWarn> getPage(Page<StoreWarn> page, StoreWarn storeWarn) {
		EntityWrapper<StoreWarn> wrapper = new EntityWrapper<>(storeWarn);
		return selectPage(page, wrapper);
	}
	

}
