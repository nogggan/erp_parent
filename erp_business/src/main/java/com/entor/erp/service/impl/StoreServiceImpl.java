package com.entor.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.StoreMapper;
import com.entor.erp.entity.Store;
import com.entor.erp.service.IStoreService;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store>
						implements IStoreService{

	@Override
	public List<Store> findListFilterPwd() {
		List<Store> stores = selectList(null);
		stores.stream().forEach(x->{
			x.getEmp().setPassword("******");
		});
		return stores;
	}

}
