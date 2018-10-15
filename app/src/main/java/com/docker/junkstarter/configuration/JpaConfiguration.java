package com.docker.junkstarter.configuration;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;

@Configuration
@EnableJpaRepositories(basePackages = "com.docker.junkstarter.repositories",
		entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class JpaConfiguration {
	
	@Autowired
	private YAMLConfig config;

	/*
	 * Configure HikariCP pooled DataSource.
	 */
	@Bean
	@Primary	
	public DataSource dataSource() {
			// Set password to connect to postgres using Docker secrets.
			// try(BufferedReader br = new BufferedReader(new FileReader("/run/secrets/postgres-password"))) {
			// 	StringBuilder sb = new StringBuilder();
			// 	String line = br.readLine();
	
			// 	while (line != null) {
			// 		sb.append(line);
			// 		sb.append(System.lineSeparator());
			// 		line = br.readLine();
			// 	}
			//	dataSourceProperties.setDataPassword("gordonpass");
			// } catch (IOException e) {
			// 	System.err.println("Could not successfully load DB password file");
			// }

			return ConnectionPool.getDataSourceFromConfig(config, new MetricRegistry(), new HealthCheckRegistry());
	}

	/*
	 * Entity Manager Factory setup.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] { "com.docker.junkstarter.model","com.docker.junkstarter.repositories" });
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;
	}

	/*
	 * Provider specific adapter.
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}

	/*
	 * Provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", config.getHibernate().getDialect());
		properties.put("hibernate.hbm2ddl.auto", config.getHibernate().getHbm2ddl().getMethod());
		properties.put("hibernate.show_sql", config.getHibernate().getShowSql());
		properties.put("hibernate.format_sql", config.getHibernate().getFormatSql());
		if(StringUtils.isNotEmpty(config.getDefaultSchema())){
			properties.put("hibernate.default_schema", config.getDefaultSchema());
		}
		return properties;
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

}
