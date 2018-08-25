package com.entor.erp.util;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;

public class ValidatorUtils {
	
	public static final <E> void  validatorList(List<E> data,Class<?> ... groups) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		for(E e : data) {
			Set<ConstraintViolation<E>> validate = validator.validate(e, groups);
			if(validate != null && !validate.isEmpty()) {
				ConstraintViolation<E> next = validate.iterator().next();
				String message = next.getMessage();
				throw new GlobalException(Result.error(ResultType.PARA_ERROR,String.format("参数异常:%s", message)));
			}
		}
	}

}
