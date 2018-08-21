package com.entor.erp.service;

import java.util.List;

import com.entor.erp.entity.Dep;

public interface IDepService {
	
	List<Dep> findByList();
	
	boolean addDep(Dep dep);
	
	boolean updateDep(Dep dep);

}
