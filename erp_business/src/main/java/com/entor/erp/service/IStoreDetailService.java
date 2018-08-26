package com.entor.erp.service;

import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.StoreDetail;

public interface IStoreDetailService extends IService<StoreDetail>{
	
	StoreDetail findByStoreAndGoods(Long storeUuid,Long goodsUuid);

}
