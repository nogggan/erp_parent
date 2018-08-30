package com.entor.erp.service;

import javax.servlet.http.HttpServletResponse;

import com.entor.erp.entity.Emp;

public interface IEmpService {

	void login(HttpServletResponse response,Emp emp);
	
	Emp getByUserName(String username);
	
	Emp getEmpByToken(String token);
	
	void removeEmpByToken(String token);

}
