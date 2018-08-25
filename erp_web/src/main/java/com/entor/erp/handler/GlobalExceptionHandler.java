package com.entor.erp.handler;

import java.util.List;
import java.util.stream.Stream;

import javax.validation.ConstraintViolationException;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value= {Exception.class})
	public Result<String> handle(Exception e){
		if(e instanceof GlobalException) {
			GlobalException exception = (GlobalException) e;
			return exception.getResult();
		}else if(e instanceof BindException) {
			BindException exception = (BindException) e;
			List<FieldError> fieldErrors = exception.getFieldErrors();
			FieldError fieldError = fieldErrors.get(0);
			String message = fieldError.getDefaultMessage();
			return Result.error(ResultType.PARA_ERROR, message);
		}else if(e instanceof HttpRequestMethodNotSupportedException) {
			HttpRequestMethodNotSupportedException exception = 
					(HttpRequestMethodNotSupportedException) e;
			String method = exception.getMethod();
			String[] supportedMethods = exception.getSupportedMethods();
			String supportMethods = Stream.of(supportedMethods).findAny().get();
			String msg = String.format( "不支持（%s）方法，支持的方法->(%s)", method,supportMethods);
			return Result.error(ResultType.METHOD_NOT_SUPPORT, msg);
		}else if(e instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) e;
			String msg = exception.getMessage();
			return Result.error(ResultType.ARGUMENT_NOT_MATCH, msg);
		}else if(e instanceof ConstraintViolationException) {
			ConstraintViolationException exception = (ConstraintViolationException) e;
			String message = exception.getMessage();
			return Result.error(ResultType.PARA_ERROR, message.substring(message.indexOf(":")+1));
		}
		e.printStackTrace();
		return Result.error(ResultType.ERROR, "服务器内部错误");
	}

}
