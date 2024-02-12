package com.spoparty.batch.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.spoparty.batch.Exception.ApiRequestFailedException;
import com.spoparty.batch.entity.League;
import com.spoparty.batch.entity.Season;
import com.spoparty.batch.entity.SeasonLeague;
import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.scheduler.model.LeagueResponse;
import com.spoparty.batch.step.seasonLeagueJpaStepConfiguration;
import com.spoparty.batch.util.EntityParser;
import com.spoparty.batch.util.FootballApiUtil;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataUpdateJobConfiguration {

	private final seasonLeagueJpaStepConfiguration seasonLeagueJpaStepConfiguration;

	@Bean
	public Job jpaJob(JobRepository jobRepository, Step seasonLeagueStep) {
		return new JobBuilder("jpaJob", jobRepository)
			.start(seasonLeagueStep)
			.build();
	}


	// @Bean
	// public ItemWriter<SeasonLeague> itemWriter() {
	// 	return items -> {
	// 		log.info(">>>>>>>>> ItemWrite");
	//
	// 			for (SeasonLeague seasonLeague : items) {
	// 				log.info("팀 정보 = {}");
	// 			}
	//
	// 	};
	// }


}
