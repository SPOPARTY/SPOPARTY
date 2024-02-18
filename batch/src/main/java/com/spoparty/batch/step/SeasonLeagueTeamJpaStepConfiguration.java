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
import com.spoparty.batch.Exception.ApiWrongDataResponseException;
import com.spoparty.batch.entity.SeasonLeague;
import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.entity.Team;
import com.spoparty.batch.scheduler.model.CoachResponse;
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
			.<SeasonLeagueTeam, SeasonLeagueTeam>chunk(chunkSize, transactionManager)
			.reader(seasonLeagueTeamjpaPagingItemReader())
			.processor(seasonLeagueTeamprocessor())
			.writer(seasonLeagueTeamjpaItemWriter())
			.faultTolerant()
			.retry(ApiRequestFailedException.class)
			.retry(DataAccessResourceFailureException.class)
			.retry(ApiWrongDataResponseException.class)
			.retryLimit(3)
			.build();
	}

	@Bean
	public ItemReader<SeasonLeagueTeam> seasonLeagueTeamjpaPagingItemReader() {
		return new JpaPagingItemReaderBuilder<SeasonLeagueTeam>()
			.name("seasonLeagueTeamjpaPagingReader")
			.entityManagerFactory(entityManagerFactory)
			.pageSize(chunkSize)
			.queryString("SELECT st FROM SeasonLeagueTeam st")
			.build();
	}

	@Bean
	public ItemProcessor<SeasonLeagueTeam, SeasonLeagueTeam> seasonLeagueTeamprocessor() {
		return new ItemProcessor<SeasonLeagueTeam, SeasonLeagueTeam>() {
			@Override
			public SeasonLeagueTeam process(SeasonLeagueTeam item) throws Exception {

				log.info(">>>>>>>id>>>>>> " + item.getId());

				String teamId = String.valueOf(item.getTeam().getId());
				MultiValueMap<String, String> paramsTeam = new LinkedMultiValueMap<>();
				paramsTeam.add("id", teamId);

				ResponseEntity teamResponse = footballApiUtil.sendRequest("/teams", paramsTeam, TeamResponse.class);

				MultiValueMap<String, String> paramsCoach = new LinkedMultiValueMap<>();
				paramsCoach.add("team", teamId);
				ResponseEntity coachResponse = footballApiUtil.sendRequest("/coachs", paramsCoach, CoachResponse.class);


				if (teamResponse.getStatusCode() != HttpStatus.OK || coachResponse.getStatusCode() != HttpStatus.OK) {
					throw new ApiRequestFailedException("API 요청에 실패하였습니다.");
				}
				return entityParser.seasonLeagueTeamParser(item, (TeamResponse)teamResponse.getBody(), (CoachResponse)coachResponse.getBody());

			}

			;
		};
	}

	@Bean
	public ItemWriter<SeasonLeagueTeam> seasonLeagueTeamjpaItemWriter() {
		return new JpaItemWriterBuilder<SeasonLeagueTeam>()
			.entityManagerFactory(entityManagerFactory)
			.build();
	}
}
