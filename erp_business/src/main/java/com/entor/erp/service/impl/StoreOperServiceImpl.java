package com.entor.erp.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.StoreOperMapper;
import com.entor.erp.entity.StoreOper;
import com.entor.erp.service.IStoreOperService;

@Service
public class StoreOperServiceImpl extends ServiceImpl<StoreOperMapper, StoreOper>
						implements IStoreOperService{

}
