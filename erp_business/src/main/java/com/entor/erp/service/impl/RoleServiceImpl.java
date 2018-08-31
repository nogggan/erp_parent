package com.entor.erp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.RoleMapper;
import com.entor.erp.entity.Role;
import com.entor.erp.entity.Tree;
import com.entor.erp.service.IRoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
						implements IRoleService{

	@Override
	public Page<Role> getPage(Page<Role> page, Role role) {
		EntityWrapper<Role> wrapper = new EntityWrapper<>(role);
		return selectPage(page, wrapper);
	}

	@Override
	public List<Tree> getRoleTree(Long empid) {
		//查询该员工所拥有的角色
		List<Role> empRoles = baseMapper.getRoleByEmpId(empid);
		List<Role> roles = selectList(null);
		List<Tree> trees = new ArrayList<>();
		roles.stream().forEach(role->{
			Tree tree = new Tree();
			tree.setId(role.getUuid().toString());
			tree.setText(role.getName());
			if(empRoles.contains(role))
				tree.setChecked(true);
			trees.add(tree);
		});
		return trees;
	}
	
}
