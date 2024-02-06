package com.spoparty.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Configuration
public class JpaConfig {


	@PersistenceContext(unitName = "springBatchEntityManager")
	private EntityManager springBatchEntityManager;

}