package com.entor.erp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.entor.erp.entity.Emp;
import com.entor.erp.interceptor.HttpLogInterceptor;
import com.entor.erp.resolver.DateMethodArgumentResolver;
import com.entor.erp.resolver.EmpHandlerMethodArgumentResolver;

@Configuration
@EnableScheduling
public class WebConfiguration implements WebMvcConfigurer{
	
	@Autowired
	private EmpHandlerMethodArgumentResolver empHandlerMethodArgumentResolver;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/list").setViewName("dep/list");
		registry.addViewController("/orders").setViewName("orders/list");
		registry.addViewController("/goods").setViewName("goods/list");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HttpLogInterceptor());
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
		resolvers.add(empHandlerMethodArgumentResolver);
	}
	
//	@Bean
	public ErpErrorAttribute erpErrorAttribute() {
		return new ErpErrorAttribute();
	}
	
}
