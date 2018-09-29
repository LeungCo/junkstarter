package com.docker.junkstarter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.docker.junkstarter.service.CustomerService;
import com.docker.junkstarter.service.CustomerServiceImpl;
import com.docker.junkstarter.service.EventService;
import com.docker.junkstarter.service.EventServiceImpl;
import com.docker.junkstarter.service.OrderService;
import com.docker.junkstarter.service.OrderServiceImpl;
import com.docker.junkstarter.service.ProductService;
import com.docker.junkstarter.service.ProductServiceImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class BeanConfiguration {

	@Bean
	public CustomerService customerService() {
		return new CustomerServiceImpl();
	}
	
	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl();
	}
	
	@Bean
	public ProductService productService() {
		return new ProductServiceImpl();
	}
	
	@Bean
	public EventService eventService() {
		return new EventServiceImpl();
	}

	// Implement C3P0 connection pooling
	@Bean
	@ConfigurationProperties("junkstarter.datasource")
	public ComboPooledDataSource dataSource() {
	    return new ComboPooledDataSource();
	}
}
