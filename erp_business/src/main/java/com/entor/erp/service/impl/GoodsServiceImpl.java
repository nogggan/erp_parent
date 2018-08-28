package com.entor.erp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.GoodsMapper;
import com.entor.erp.entity.Goods;
import com.entor.erp.service.IGoodsService;
import com.entor.erp.vo.GoodsCountVo;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
							implements IGoodsService{

	@Override
	public Page<Goods> getPage(Page<Goods> page, Goods goods) {
		EntityWrapper<Goods> wrapper = new EntityWrapper<>(goods);
		return selectPage(page,wrapper );
	}

	@Override
	public Page<GoodsCountVo> getGoodsCountInfo(Page<GoodsCountVo> page, Date startDate, Date endDate) {
		List<GoodsCountVo> goodsCountVos = baseMapper.getGoodsCount(page,startDate,endDate);
		page.setRecords(goodsCountVos);
		return page;
	}

	@Override
	public List<GoodsCountVo> getGoodsCountInfo(Date startDate, Date endDate) {
		return baseMapper.getGoodsCount(startDate,endDate);
	}
}
