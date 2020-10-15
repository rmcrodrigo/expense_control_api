package com.rodmontiel.ec.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rodmontiel.ec.ExpensesControlApplication;


@Configuration
public class MvcConf implements WebMvcConfigurer {
	
	@Bean
	@Scope("singleton")
	public Logger createLogger() {
		return LoggerFactory.getLogger(ExpensesControlApplication.class);
	}
}
