package com.entor.erp.service;

import java.util.Date;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.Goods;
import com.entor.erp.vo.GoodsCountVo;

public interface IGoodsService extends IService<Goods>{
	
	Page<Goods> getPage(Page<Goods> page,Goods goods);
	
	Page<GoodsCountVo> getGoodsCountInfo(Page<GoodsCountVo> page,Date startDate,Date endDate);

}
