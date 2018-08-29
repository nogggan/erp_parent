package com.entor.erp.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.StoreWarn;

public interface IStoreWarnService extends IService<StoreWarn>{

	Page<StoreWarn> getPage(Page<StoreWarn> page,StoreWarn storeWarn);
	
}
