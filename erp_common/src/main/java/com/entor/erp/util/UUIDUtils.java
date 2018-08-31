package com.entor.erp.util;

import java.util.Base64;
import java.util.UUID;


public class UUIDUtils {
	
	public static final String uuid() {
		return UUID.randomUUID().toString().replace("-", "")+
				Base64.getEncoder().encodeToString(String.valueOf(System.currentTimeMillis()).getBytes());
	}
	
}