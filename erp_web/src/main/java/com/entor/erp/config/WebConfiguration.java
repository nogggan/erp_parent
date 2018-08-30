package com.entor.erp.config;

import java.util.List;

import org.apache.activemq.filter.function.regexMatchFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.entor.erp.handler.ResultHandlerMethodReturnValueHandler;
import com.entor.erp.interceptor.AccessControlHandlerInterceptor;
import com.entor.erp.interceptor.HttpLogInterceptor;
import com.entor.erp.interceptor.ResolveTokenHandlerInterceptor;
import com.entor.erp.resolver.DateMethodArgumentResolver;
import com.entor.erp.resolver.EmpHandlerMethodArgumentResolver;

@Configuration
@EnableScheduling
public class WebConfiguration implements WebMvcConfigurer{
	
	@Autowired
	private ResolveTokenHandlerInterceptor tokenInterceptor;
	
	@Autowired
	private ResultHandlerMethodReturnValueHandler resultHandlerMethodReturnValueHandler;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/list").setViewName("dep/list");
		registry.addViewController("/orders").setViewName("orders/list");
		registry.addViewController("/goods").setViewName("goods/list");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HttpLogInterceptor());
		registry.addInterceptor(tokenInterceptor);
		registry.addInterceptor(new AccessControlHandlerInterceptor());
	}
	
	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		handlers.add(resultHandlerMethodReturnValueHandler);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("POST","GET","DELETE","PUT").allowCredentials(false).maxAge(3600);
	}
	
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
		return postProcessor;
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new DateMethodArgumentResolver());
		resolvers.add(new EmpHandlerMethodArgumentResolver());
	}
	
//	@Bean
	public ErpErrorAttribute erpErrorAttribute() {
		return new ErpErrorAttribute();
	}
	
}
