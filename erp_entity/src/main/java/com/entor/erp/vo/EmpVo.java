package com.entor.erp.vo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmpVo {

	@NotBlank(message="用户名不能为空")
	private String username;
    
	@NotBlank(message="密码不能为空")
    private String password;
	
}
