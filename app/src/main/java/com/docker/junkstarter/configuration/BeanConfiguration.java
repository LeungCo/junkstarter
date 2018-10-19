package com.docker.junkstarter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.docker.junkstarter.service.EventService;
import com.docker.junkstarter.service.EventServiceImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class BeanConfiguration {
	
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
