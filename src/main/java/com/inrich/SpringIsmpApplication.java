package com.inrich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement  //开启事务支持
public class SpringIsmpApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(SpringIsmpApplication.class, args);
	}
	
	
}
