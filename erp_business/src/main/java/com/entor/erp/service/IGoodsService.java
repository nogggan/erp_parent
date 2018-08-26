package com.entor.erp.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.Goods;

public interface IGoodsService extends IService<Goods>{
	
	Page<Goods> getPage(Page<Goods> page,Goods goods);

}
