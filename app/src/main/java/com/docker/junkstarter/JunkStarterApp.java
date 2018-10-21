package com.docker.junkstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.docker.junkstarter.configuration.PersistenceContext;
import com.docker.junkstarter.security.JwtFilter;


@Import(PersistenceContext.class)
@SpringBootApplication(scanBasePackages={"com.docker.junkstarter"})
public class JunkStarterApp {

	@Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/purchase/*");

        return registrationBean;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(JunkStarterApp.class, args);
	}
}
