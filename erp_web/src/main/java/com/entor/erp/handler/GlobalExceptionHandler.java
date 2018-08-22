package com.entor.erp.handler;

import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value= {Exception.class})
	public Result<String> handle(Exception e){
		e.printStackTrace();
		if(e instanceof GlobalException) {
			GlobalException exception = (GlobalException) e;
			return exception.getResult();
		}else if(e instanceof BindException) {
			BindException exception = (BindException) e;
			List<FieldError> fieldErrors = exception.getFieldErrors();
			FieldError fieldError = fieldErrors.get(0);
			String message = fieldError.getDefaultMessage();
			return Result.error(ResultType.PARA_ERROR, message);
		}
		return Result.error(ResultType.ERROR, "服务器内部错误");
	}

}
