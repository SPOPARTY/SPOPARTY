package com.spoparty.batch;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@EnableJpaRepositories(
	entityManagerFactoryRef = "springBatchTXManagerFactory",//엔티티 매니저 이름
	transactionManagerRef = "springBatchTXManager"	// 트랜잭션 매니저 이름
)
@Configuration
public class DatabaseConfig {

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
	@BatchDataSource
	PlatformTransactionManager metaTxManager() {
		return new DataSourceTransactionManager((logDb()));
	}






	@Bean
	@Primary  	// datasource를 이용할 경우 자동으로 주입
	@ConfigurationProperties(prefix = "spring.default.datasource.hikari")
	DataSource springBatchDb() {
		DataSourceBuilder builder = DataSourceBuilder.create();
		builder.type(HikariDataSource.class);
		return builder.build();
	}



	@Bean(name = "springBatchTXManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean contentsEntityManagerFactory(EntityManagerFactoryBuilder builder) {

		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.physical_naming_strategy" , "com.spoparty.batch.util.SnakeCaseNamingStrategy");

		return builder
			.dataSource(springBatchDb())
			.persistenceUnit("springBatchEntityManager")
			.packages("com.spoparty.batch")
			.properties(properties)
			.build();
	}


	@Bean(name = "springBatchTXManager")
	@Primary
	PlatformTransactionManager springBatchTxManager(final @Qualifier("springBatchTXManagerFactory") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
		return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
	}


}