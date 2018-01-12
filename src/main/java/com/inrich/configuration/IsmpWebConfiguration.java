package com.inrich.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.inrich.interceptor.LoginInterceptor;
import com.inrich.interceptor.PassInterceptor;


@Component
public class IsmpWebConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	private PassInterceptor passInterceptor;
	@Autowired
	private LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(passInterceptor);
		registry.addInterceptor(loginInterceptor).addPathPatterns("/admin/*");
		super.addInterceptors(registry);
	}
	
	
}
