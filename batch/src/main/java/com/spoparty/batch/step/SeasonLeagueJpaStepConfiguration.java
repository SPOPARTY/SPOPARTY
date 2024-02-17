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
import com.spoparty.batch.scheduler.model.LeagueResponse;
import com.spoparty.batch.util.EntityParser;
import com.spoparty.batch.util.FootballApiUtil;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class SeasonLeagueJpaStepConfiguration {

	private final EntityManagerFactory entityManagerFactory;
	private final FootballApiUtil footballApiUtil;

	private static final int chunkSize = 10;
	private final EntityParser entityParser;

	@Bean
	@JobScope
	public Step seasonLeagueStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("jpaStep", jobRepository)
			.<SeasonLeague, SeasonLeague>chunk(chunkSize, transactionManager)
			.reader(seasonLeaguejpaPagingItemReader())
			.processor(seasonLeagueprocessor())
			.writer(seasonLeaguejpaItemWriter())
			.faultTolerant()
			.retry(ApiRequestFailedException.class)
			.retry(DataAccessResourceFailureException.class)
			.retryLimit(3)
			.build();
	}

	@Bean
	public ItemReader<SeasonLeague> seasonLeaguejpaPagingItemReader() {
		return new JpaPagingItemReaderBuilder<SeasonLeague>()
			.name("jpaTestReader")
			.entityManagerFactory(entityManagerFactory)
			.pageSize(chunkSize)
			.queryString("SELECT sl FROM SeasonLeague sl")
			.build();
	}

	@Bean
	public ItemProcessor<SeasonLeague, SeasonLeague> seasonLeagueprocessor() {
		return new ItemProcessor<SeasonLeague, SeasonLeague>() {
			@Override
			public SeasonLeague process(SeasonLeague item) throws Exception {

				log.info(">>>>>>>id>>>>>> " + item.getId());

				String leagueId = String.valueOf(item.getLeague().getId());
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				params.add("season", "2023");
				params.add("id", leagueId);

				ResponseEntity response = footballApiUtil.sendRequest("/leagues", params, LeagueResponse.class);

				if (response.getStatusCode() != HttpStatus.OK) {
					throw new ApiRequestFailedException("API 요청에 실패하였습니다.");
				}
				return entityParser.seasonLeagueParser(item.getId(), (LeagueResponse)response.getBody());

			}

			;
		};
	}

	@Bean
	public ItemWriter<SeasonLeague> seasonLeaguejpaItemWriter() {
		return new JpaItemWriterBuilder<SeasonLeague>()
			.entityManagerFactory(entityManagerFactory)
			.build();
	}
}
