package com.entor.erp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entor.erp.entity.Menu;

@Repository
public interface MenuMapper extends BaseMapper<Menu>{
	
	List<Menu> getMenuByRoleId(Long roleid);

}
