package com.entor.erp.exception;

import com.entor.erp.result.Result;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private Result<String> result;

	public GlobalException() {
		super();
	}

	public GlobalException(Result<String> result) {
		this.result = result;
	}
	
	
	
}
