package com.docker.junkstarter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "datasource.junkstarter")
public class YAMLConfig {

	private String url;
	private String username;
	private String password;
	private String driverClassName;
	private String defaultSchema;
	private int maxPoolSize;
	private final Hibernate hibernate = new Hibernate();

	public static class Hibernate {
		private final Hbm2ddl hbm2ddl = new Hbm2ddl();
		private String showSql;
		private String formatSql;
		private String dialect;

		public String getShowSql() {
			return showSql;
		}

		public void setShowSql(String showSql) {
			this.showSql = showSql;
		}

		public String getFormatSql() {
			return formatSql;
		}

		public void setFormatSql(String formatSql) {
			this.formatSql = formatSql;
		}

		public String getDialect() {
			return dialect;
		}

		public void setDialect(String dialect) {
			this.dialect = dialect;
		}

		public Hbm2ddl getHbm2ddl() {
			return hbm2ddl;
		}
	}

	public static class Hbm2ddl {
		private String method;

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getDefaultSchema() {
		return defaultSchema;
	}

	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public Hibernate getHibernate() {
		return hibernate;
	}
}
