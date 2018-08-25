package com.entor.erp.vo;

import javax.validation.constraints.NotNull;

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

}
