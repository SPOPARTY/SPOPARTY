package com.spoparty.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.spoparty.batch.Exception.ApiRequestFailedException;
import com.spoparty.batch.entity.SeasonLeague;
import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.entity.Team;
import com.spoparty.batch.scheduler.model.LeagueResponse;
import com.spoparty.batch.scheduler.model.TeamResponse;
import com.spoparty.batch.util.EntityParser;
import com.spoparty.batch.util.FootballApiUtil;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class SeasonLeagueTeamJpaStepConfiguration {

	private final EntityManagerFactory entityManagerFactory;
	private final FootballApiUtil footballApiUtil;

	private static final int chunkSize = 10;
	private final EntityParser entityParser;

	@Bean
	@JobScope
	public Step seasonLeagueTeamStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("jpaTeamStep", jobRepository)
			.<SeasonLeague, SeasonLeague>chunk(chunkSize, transactionManager)
			.reader(seasonLeagueTeamjpaPagingItemReader())
			.processor(seasonLeagueTeamprocessor())
			.writer(seasonLeagueTeamjpaItemWriter())
			.faultTolerant()
			.retry(ApiRequestFailedException.class)
			.retry(DataAccessResourceFailureException.class)
			.retryLimit(3)
			.build();
	}

	@Bean
	public ItemReader<SeasonLeague> seasonLeagueTeamjpaPagingItemReader() {
		return new JpaPagingItemReaderBuilder<SeasonLeague>()
			.name("seasonLeagueTeamjpaPagingReader")
			.entityManagerFactory(entityManagerFactory)
			.pageSize(chunkSize)
			.queryString("SELECT st FROM SeasonLeagueTeam st")
			.build();
	}

	@Bean
	public ItemProcessor<SeasonLeague, SeasonLeague> seasonLeagueTeamprocessor() {
		return new ItemProcessor<SeasonLeagueTeam, SeasonLeagueTeam>() {
			@Override
			public SeasonLeagueTeam process(SeasonLeagueTeam item) throws Exception {

				log.info(">>>>>>>id>>>>>> " + item.getId());

				String teamId = String.valueOf(item.getTeam());
				MultiValueMap<String, String> paramsTeam = new LinkedMultiValueMap<>();
				paramsTeam.add("id", teamId);

				ResponseEntity teamResponse = footballApiUtil.sendRequest("/teams", params, LeagueResponse.class);

				MultiValueMap<String, String> paramsCoach = new LinkedMultiValueMap<>();
				params.add("team", teamId);
				ResponseEntity coa

				if (response.getStatusCode() != HttpStatus.OK) {
					throw new ApiRequestFailedException("API 요청에 실패하였습니다.");
				}
				return entityParser.seasonLeagueTeamParser(item, (TeamResponse)teamResponse.getBody());

			}

			;
		};
	}

	@Bean
	public ItemWriter<SeasonLeague> seasonLeagueTeamjpaItemWriter() {
		return new JpaItemWriterBuilder<SeasonLeague>()
			.entityManagerFactory(entityManagerFactory)
			.build();
	}
}
