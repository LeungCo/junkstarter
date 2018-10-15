package com.docker.junkstarter.configuration;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {
    private ConnectionPool() {

    }
    /*
     * Expects a config in the following format
     *
     * poolName = "test pool"
     * jdbcUrl = ""
     * maximumPoolSize = 10
     * minimumIdle = 2
     * username = ""
     * password = ""
     * cachePrepStmts = true
     * prepStmtCacheSize = 256
     * prepStmtCacheSqlLimit = 2048
     * useServerPrepStmts = true
     *
     * Let HikariCP bleed out here on purpose
     */
    public static HikariDataSource getDataSourceFromConfig(
        YAMLConfig conf
        , MetricRegistry metricRegistry
        , HealthCheckRegistry healthCheckRegistry) {

        HikariConfig jdbcConfig = new HikariConfig();

        //Database Configuration   
        jdbcConfig.setJdbcUrl(conf.getUrl());      
        jdbcConfig.setUsername(conf.getUsername());
        jdbcConfig.setPassword(conf.getPassword());
        jdbcConfig.setMaximumPoolSize(conf.getMaxPoolSize());      
        jdbcConfig.setDriverClassName(conf.getDriverClassName());
        
        //Performance Tweaks
        jdbcConfig.addDataSourceProperty("cachePrepStmts","true");
        jdbcConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        jdbcConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        //jdbcConfig.addDataSourceProperty("useServerPrepStmts", conf.getBoolean("useServerPrepStmts"));
        

        // Add HealthCheck
        jdbcConfig.setHealthCheckRegistry(healthCheckRegistry);

        // Add Metrics
        jdbcConfig.setMetricRegistry(metricRegistry);
        
        return new HikariDataSource(jdbcConfig);
    }
}
