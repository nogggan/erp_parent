package com.entor.erp.resolver;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;

public class DateMethodArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType()==Date.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String parameterName = parameter.getParameterName();
		String dateParamValue = webRequest.getParameter(parameterName);
		Date date = null;
		if(!StringUtils.isEmpty(dateParamValue)) {
			try {
				date = simpleDateFormat.parse(dateParamValue);
			} catch (Exception e) {
				throw new GlobalException(Result.error(ResultType.FORMAT_ERROR, "不是一个日期，格式化失败，期望格式(yyyy-MM-dd)"));
			}
		}
		return date;
	}

}
