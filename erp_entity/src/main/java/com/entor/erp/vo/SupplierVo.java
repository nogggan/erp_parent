package com.entor.erp.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SupplierVo {
	
	@NotNull(message="供应商不能为空")
	private Long uuid;
	
	@NotBlank(message="供应商类型不能为空")
	@Min(value=1,message="供应商类型不合法")
	@Max(value=2,message="供应商类型不合法")
	private String type;

}
