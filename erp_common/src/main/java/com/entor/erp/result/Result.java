package com.entor.erp.result;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.entor.erp.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
	
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	@JsonSerialize(using=DateSerializer.class)
	private Date timestamp;
	
	public static final <E> Result<E> success(E data) {
		return new Result<E>().setCode(200).setData(data).setMsg("处理成功").setTimestamp(new Date());
	}
	
	public static final <E> Result<E> error(Integer code,String msg){
		return new Result<E>().setCode(code).setMsg(msg).setTimestamp(new Date());
	}
 	
}
