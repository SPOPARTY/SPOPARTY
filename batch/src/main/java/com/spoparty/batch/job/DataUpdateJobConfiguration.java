package com.spoparty.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spoparty.batch.step.SeasonLeagueJpaStepConfiguration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataUpdateJobConfiguration {

	private final SeasonLeagueJpaStepConfiguration seasonLeagueJpaStepConfiguration;

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
