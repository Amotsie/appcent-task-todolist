package com.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.todolist.filter.AuthFilter;

@SpringBootApplication
public class App6Application {

	public static void main(String[] args) {
		SpringApplication.run(App6Application.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
		
		FilterRegistrationBean<AuthFilter> regBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		regBean.setFilter(authFilter);
		regBean.addUrlPatterns("/api/todos/*");
		return regBean;
	}
}
