package com.entor.erp.service.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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

	@Override
	public Dep getById(Long id) {
		return selectById(id);
	}

	@Override
	public Page<Dep> getPage(Page<Dep> page,Dep dep) {
		EntityWrapper<Dep> wrapper = new EntityWrapper<>();
		if(!StringUtils.isEmpty(dep.getTele()))
			wrapper.like("tele", dep.getTele());
		if(!StringUtils.isEmpty(dep.getName()))
			wrapper.like("name", dep.getName());
		if(dep.getUuid() != null)
			wrapper.eq("uuid", dep.getUuid());
		wrapper.orderDesc(Arrays.asList("uuid"));
		return selectPage(page,wrapper);
	}

	@Override
	public boolean delete(Long id) {
		return deleteById(id);
	}


}
