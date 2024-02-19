package com.spoparty.batch.step;

import com.spoparty.batch.Exception.ApiRequestFailedException;
import com.spoparty.batch.Exception.ApiWrongDataResponseException;
import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.entity.SeasonLeagueTeamPlayer;
import com.spoparty.batch.scheduler.model.*;
import com.spoparty.batch.util.EntityParser;
import com.spoparty.batch.util.FootballApiUtil;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
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

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SeasonLeagueTeamPlayerStepConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final FootballApiUtil footballApiUtil;

    private static final int chunkSize = 10;
    private final EntityParser entityParser;

    private final SeasonLeagueTeamJpaStepConfiguration seasonLeagueTeamJpaStepConfiguration;
    @Bean
    @JobScope
    public Step seasonLeagueTeamPlayerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("jpaPlayerStep", jobRepository)
                .<SeasonLeagueTeam, List<SeasonLeagueTeamPlayer>>chunk(chunkSize, transactionManager)
                .reader(seasonLeagueTeamJpaStepConfiguration.seasonLeagueTeamjpaPagingItemReader())
                .processor(seasonLeagueTeamPlayerprocessor())
                .writer(seasonLeagueTeamPlayerjpaItemWriter())
                .faultTolerant()
                .retry(ApiRequestFailedException.class)
                .retry(DataAccessResourceFailureException.class)
                .retry(ApiWrongDataResponseException.class)
                .retryLimit(3)
                .build();
    }



    @Bean
    public ItemProcessor<SeasonLeagueTeam, List<SeasonLeagueTeamPlayer>> seasonLeagueTeamPlayerprocessor() {
        return new ItemProcessor<SeasonLeagueTeam, List<SeasonLeagueTeamPlayer>>() {
            @Override
            public List<SeasonLeagueTeamPlayer> process(SeasonLeagueTeam item) throws Exception {

                log.info(">>>>>>>id>>>>>> " + item.getId());

                String teamId = String.valueOf(item.getTeam().getId());
                MultiValueMap<String, String> paramsTeam = new LinkedMultiValueMap<>();
                paramsTeam.add("team", teamId);
                paramsTeam.add("season", "2023");
                paramsTeam.add("page", "1");

                ResponseEntity response = footballApiUtil.sendRequest("/players", paramsTeam, PlayerResponse.class);



                if (response.getStatusCode() != HttpStatus.OK ) {
                    throw new ApiRequestFailedException("API 요청에 실패하였습니다.");
                }


                PlayerResponse playerResponse = (PlayerResponse)response.getBody();
                int totalPage = playerResponse.getPaging().getTotal();

                List<Player> players = new ArrayList<>();

                for (Players playerInfo : playerResponse.getResponse()) {
                    players.add(playerInfo.getPlayer());
                }

                for (int page = 2; page <= totalPage; page++) {
                    paramsTeam.set("page", String.valueOf(page));

                    response = footballApiUtil.sendRequest("/players", paramsTeam, PlayerResponse.class);
                    playerResponse = (PlayerResponse)response.getBody();

                    for (Players playerInfo : playerResponse.getResponse()) {
                        players.add(playerInfo.getPlayer());
                    }
                }


                return entityParser.seasonLeagueTeamPlayerParser(item, players);


            }

            ;
        };
    }

//    @Bean
//    public ItemWriter<List<SeasonLeagueTeamPlayer>> seasonLeagueTeamPlayerjpaItemWriter() {
//        return new JpaItemWriterBuilder<List<SeasonLeagueTeamPlayer>>()
//                .entityManagerFactory(entityManagerFactory)
//                .build();
//    }

    @Bean
    private JpaItemListWriter<SeasonLeagueTeamPlayer> seasonLeagueTeamPlaeryListWriter() {
        JpaItemWriter<SeasonLeagueTeamPlayer> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);

        return new JpaItemListWriter<>(writer);
    }
}
