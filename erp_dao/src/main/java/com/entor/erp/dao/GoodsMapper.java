package com.entor.erp.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.entity.Goods;
import com.entor.erp.vo.GoodsCountVo;

@Repository
public interface GoodsMapper extends BaseMapper<Goods>{
	
	List<GoodsCountVo> getGoodsCount(Page<GoodsCountVo> page,@Param("start")Date start,@Param("end")Date end);

}
