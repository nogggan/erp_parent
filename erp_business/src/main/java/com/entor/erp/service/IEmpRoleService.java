package com.entor.erp.service;

import com.baomidou.mybatisplus.service.IService;
import com.entor.erp.entity.EmpRole;

public interface IEmpRoleService extends IService<EmpRole>{

	boolean updateEmpRole(long empid,String[] ids);
	
}
