package com.docker.junkstarter.configuration;

import javax.sql.DataSource;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;

@Configuration
@ComponentScan({ "com.docker.junkstarter.jooq.tables" })
@EnableTransactionManagement
public class PersistenceContext {

	@Autowired
	private YAMLConfig config;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		return ConnectionPool.getDataSourceFromConfig(config, new MetricRegistry(), new HealthCheckRegistry());
	}

	 @Bean
	    public LazyConnectionDataSourceProxy lazyConnectionDataSource() {
	        return new LazyConnectionDataSourceProxy(dataSource());
	    }
	 
	    @Bean
	    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
	        return new TransactionAwareDataSourceProxy(lazyConnectionDataSource());
	    }
	 
	    @Bean
	    public DataSourceTransactionManager transactionManager() {
	        return new DataSourceTransactionManager(lazyConnectionDataSource());
	    }
	 
	    @Bean
	    public DataSourceConnectionProvider connectionProvider() {
	        return new DataSourceConnectionProvider(transactionAwareDataSource());
	    }
	 
	    @Bean
	    public JOOQToSpringExceptionTransformer jooqToSpringExceptionTransformer() {
	        return new JOOQToSpringExceptionTransformer();
	    }
	 
	    @Bean
	    public DefaultConfiguration configuration() {
	        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
	 
	        jooqConfiguration.set(connectionProvider());
	        jooqConfiguration.set(new DefaultExecuteListenerProvider(
	            jooqToSpringExceptionTransformer()
	        ));
	 
	        String sqlDialectName = config.getHibernate().getDialect();
	        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
	        jooqConfiguration.set(dialect);
	 
	        return jooqConfiguration;
	    }
	 
	    @Bean
	    public DefaultDSLContext dsl() {
	        return new DefaultDSLContext(configuration());
	    }
	 
	    @Bean
	    public DataSourceInitializer dataSourceInitializer() {
	        DataSourceInitializer initializer = new DataSourceInitializer();
	        initializer.setDataSource(dataSource());
	 
	        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	        populator.addScript(
	                new ClassPathResource("schema.sql")
	        );
	 
	        initializer.setDatabasePopulator(populator);
	        return initializer;
	    }
}
