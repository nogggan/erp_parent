package com.entor.erp.handler;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.entor.erp.result.Result;

@Component
public class ResultHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler{

	@Autowired
	private MappingJackson2HttpMessageConverter converter;
	
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getParameterType()==Result.class;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		Result<?> result = (Result<?>) returnValue;
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
		converter.write(result, mediaType, outputMessage);
		mavContainer.setRequestHandled(true);
	}

}
