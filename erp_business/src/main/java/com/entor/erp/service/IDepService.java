package com.entor.erp.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.entity.Dep;

public interface IDepService {
	
	List<Dep> findByList();
	
	boolean addDep(Dep dep);
	
	boolean updateDep(Dep dep);
	
	Dep getById(Long id);
	
	Page<Dep> getPage(Page<Dep> page,Dep dep);
	
	boolean delete(Long id);
}
