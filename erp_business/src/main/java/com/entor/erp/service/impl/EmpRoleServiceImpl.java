package com.entor.erp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.EmpRoleMapper;
import com.entor.erp.entity.EmpRole;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IEmpRoleService;

@Service
public class EmpRoleServiceImpl extends ServiceImpl<EmpRoleMapper, EmpRole>
						implements IEmpRoleService{

	@Transactional
	@Override
	public boolean updateEmpRole(long empid, String[] ids) {
		if(ids==null || ids.length==0 || "".equals(ids[0])) {
			if(delete(new EntityWrapper<EmpRole>().eq("empid", empid))) {
				return true;
			}
		}
		Long[] roleIds = new Long[ids.length];
		for(int i=0;i<ids.length;i++) {
			try {
				roleIds[i] = Long.parseLong(ids[i]);
			} catch (Exception e) {
				throw new GlobalException(Result.error(ResultType.FORMAT_ERROR, "角色编号不合法"));
			}
		}
		boolean deleteRowData = delete(new EntityWrapper<EmpRole>().eq("empid", empid));
		if(deleteRowData) {
			List<EmpRole> empRoles = new ArrayList<>();
			Stream.of(roleIds).forEach(id->{
				EmpRole empRole = new EmpRole();
				empRole.setEmpid(empid);
				empRole.setRoleid(id);
				empRoles.add(empRole);
			});
			if(insertBatch(empRoles, ids.length)) {
				return true;
			}else {
				throw new GlobalException(Result.error(ResultType.ERROR, "用户角色修改失败，请重新尝试"));
			}
		}
		return false;
	}
	
}
