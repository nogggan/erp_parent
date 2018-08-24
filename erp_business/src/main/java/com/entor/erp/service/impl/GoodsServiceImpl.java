package com.entor.erp.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.GoodsMapper;
import com.entor.erp.entity.Goods;
import com.entor.erp.service.IGoodsService;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
							implements IGoodsService{

}
