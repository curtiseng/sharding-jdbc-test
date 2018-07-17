package com.kiviblog.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangzifeng
 */
@Configuration
@EnableJpaRepositories("com.kiviblog")
@EntityScan("com.kiviblog")
@EnableTransactionManagement
public class DatabaseConfiguration {


    @Bean(name = "masterHikariConfig")
    @ConfigurationProperties(prefix = "datasource.master.hikari")
    public HikariConfig masterHikariConfig() {
        return new HikariConfig();
    }

    private DataSource masterDataSource() {
        return new HikariDataSource(masterHikariConfig());
    }

    @Bean(name = "slaveHikariConfig")
    @ConfigurationProperties(prefix = "datasource.slave.hikari")
    public HikariConfig slaveHikariConfig() {
        return new HikariConfig();
    }

    private DataSource slaveDataSource() {
        return new HikariDataSource(slaveHikariConfig());
    }


    @Bean
    @Primary
    public DataSource getMasterSlaveDataSource() throws SQLException {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig.setName("ds_ms");
        masterSlaveRuleConfig.setMasterDataSourceName("ds_master");
        masterSlaveRuleConfig.setSlaveDataSourceNames(Collections.singletonList("ds_slave"));
        return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(), masterSlaveRuleConfig, new HashMap<>(1));
    }

    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(2);
        result.put("ds_master", masterDataSource());
        result.put("ds_slave", slaveDataSource());
        return result;
    }

}



