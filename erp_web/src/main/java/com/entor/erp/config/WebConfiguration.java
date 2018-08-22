package com.entor.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/list").setViewName("dep/list");
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
	
//	@Bean
	public ErpErrorAttribute erpErrorAttribute() {
		return new ErpErrorAttribute();
	}
	
}
