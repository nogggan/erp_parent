package com.entor.erp.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entor.erp.entity.StoreDetail;

@Repository
public interface StoreDetailMapper extends BaseMapper<StoreDetail>{

	int minusStoreCount(@Param("storeDetail") StoreDetail storeDetail,@Param("outNum")Integer outNum);
	
}
