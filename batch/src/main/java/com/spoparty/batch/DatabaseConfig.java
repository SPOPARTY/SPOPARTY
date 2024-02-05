package com.spoparty.batch;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {
	@Bean
	@Primary  	// datasource를 이용할 경우 자동으로 주입
	@ConfigurationProperties(prefix = "spring.default.datasource.hikari")
	DataSource springBatchDb() {
		DataSourceBuilder builder = DataSourceBuilder.create();
		builder.type(HikariDataSource.class);
		return builder.build();
	}

	@Bean
	// 메타 정보 저장
	@BatchDataSource
	@ConfigurationProperties(prefix = "spring.meta.datasource.hikari")
	DataSource logDb() {
		DataSourceBuilder builder = DataSourceBuilder.create();
		builder.type(HikariDataSource.class);
		return builder.build();
	}

	@Bean
	@Primary
	PlatformTransactionManager springBatchTxManager() {
		return new DataSourceTransactionManager((springBatchDb()));
	}

	@Bean
	@BatchDataSource
	PlatformTransactionManager metaTxManager() {
		return new DataSourceTransactionManager((logDb()));
	}

}