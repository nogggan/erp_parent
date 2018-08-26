package com.entor.erp.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.Store;

public interface IStoreService extends IService<Store>{
	
	List<Store> findListFilterPwd();

}
