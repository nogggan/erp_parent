package com.entor.erp.result;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain=true)
public class Result<T> {

	private Integer code;
	
	private String msg;
	
	private T data;
	
	public static final <E> Result<E> success(E data) {
		return new Result<E>().setCode(200).setData(data).setMsg("处理成功");
	}
	
	public static final <E> Result<E> error(Integer code,String msg){
		return new Result<E>().setCode(code).setMsg(msg);
	}
 	
}
