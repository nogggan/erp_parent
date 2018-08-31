package com.entor.erp.result;

public interface ResultType {
	
	Integer PARA_ERROR = 501;
	
	Integer FORMAT_ERROR = 400;
	
	Integer USER_NO_EXISTS = 601;
	
	Integer ERROR = 500;
	
	Integer METHOD_NOT_SUPPORT = 406;

	Integer ARGUMENT_NOT_MATCH = 407;
	
	Integer ORDERS_ERROR = 408;
	
	Integer NOT_LOGIN = 409;
	
	Integer	PERMISSION_DENIED = 600;
}
