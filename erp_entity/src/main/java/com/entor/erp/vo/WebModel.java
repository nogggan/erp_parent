package com.entor.erp.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class WebModel {

	private String msg;
	
	private String url;
	
	private String title;
	
}
