package com.entor.erp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.DepMapper;
import com.entor.erp.entity.Dep;
import com.entor.erp.service.IDepService;

@Service
public class DepServiceImpl extends ServiceImpl<DepMapper, Dep> implements IDepService{

	@Override
	public List<Dep> findByList() {
		return selectList(null);
	}

	@Override
	public boolean addDep(Dep dep) {
		return insert(dep);
	}

	@Override
	public boolean updateDep(Dep dep) {
		return updateById(dep);
	}


}
