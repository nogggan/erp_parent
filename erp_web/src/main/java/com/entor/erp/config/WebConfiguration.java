package com.entor.erp.config;

import java.util.List;

import org.apache.activemq.filter.function.regexMatchFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
import com.entor.erp.interceptor.AuthHandlerInterceptor;
import com.entor.erp.interceptor.ContextHandlerInerceptor;
import com.entor.erp.interceptor.HttpLogInterceptor;
import com.entor.erp.interceptor.ResolveTokenHandlerInterceptor;
import com.entor.erp.resolver.DateHandlerMethodArgumentResolver;
import com.entor.erp.resolver.EmpHandlerMethodArgumentResolver;

import oracle.net.aso.a;

@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
public class WebConfiguration implements WebMvcConfigurer{
	
	@Autowired
	private ResolveTokenHandlerInterceptor tokenInterceptor;
	
	@Autowired
	private ResultHandlerMethodReturnValueHandler resultHandlerMethodReturnValueHandler;
	
	@Autowired
	private AuthHandlerInterceptor authHandlerInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ContextHandlerInerceptor()).excludePathPatterns("*.js","*.css","/bootstrap/css/bootstrap.min.css");
		registry.addInterceptor(new HttpLogInterceptor()).excludePathPatterns("*.js","*.css","/bootstrap/css/bootstrap.min.css");
		registry.addInterceptor(tokenInterceptor).excludePathPatterns("*.js","*.css","/bootstrap/css/bootstrap.min.css");
		registry.addInterceptor(authHandlerInterceptor).excludePathPatterns("*.js","*.css","/bootstrap/css/bootstrap.min.css");
		registry.addInterceptor(new AccessControlHandlerInterceptor()).excludePathPatterns("*.js","*.css","/bootstrap/css/bootstrap.min.css");
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
		resolvers.add(new DateHandlerMethodArgumentResolver());
		resolvers.add(new EmpHandlerMethodArgumentResolver());
	}
	
//	@Bean
	public ErpErrorAttribute erpErrorAttribute() {
		return new ErpErrorAttribute();
	}
	
}
