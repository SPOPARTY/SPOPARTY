package com.spoparty.batch.scheduler;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.spoparty.batch.entity.CheerFixture;
import com.spoparty.batch.repository.CheerFixtureRepository;
import com.spoparty.batch.scheduler.model.Fixtures;
import com.spoparty.batch.scheduler.model.FixturesResponse;
import com.spoparty.batch.util.ApiRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

	private final ApiRequest apiRequest;
	private final CheerFixtureRepository cheerFixtureRepository;

	private List<Long> fixtureIdInProgress = new ArrayList<>();
	private int[] leagues = {1, 7, 8, 18, 15, 532, 965, 1012, 17, 292, 293, 295, 294, 48, 143, 556, 78, 529, 81, 66, 526, 119};

	@Scheduled(fixedRate = 1000*60)
	public void fixtureLive() {
		log.info("Scheduler.fixtureLive() : {}", System.currentTimeMillis());
		// System.out.println("실행!");
		//
		// MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		// queryParams.add("leauge", "1");

		// ResponseEntity<String> request = apiRequest.sendRequest("/league", queryParams);
		//
		// System.out.println(request.getBody());

	}

	@Scheduled(fixedRate = 1000*60*60*24)
	public void fixtures() {
		log.info("Scheduler.fixtures() : {}", System.currentTimeMillis());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String yesterday = LocalDate.now().minusDays(1).format(formatter);
		String tomorrow = LocalDate.now().plusDays(1).format(formatter);

		for (int leagueId : leagues){
			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("league", leagueId+"");
			queryParams.add("season", "2023");
			queryParams.add("from", yesterday);
			queryParams.add("to", tomorrow);
			ResponseEntity<?> response = apiRequest.sendRequest("/fixtures", queryParams, FixturesResponse.class);
			if (response.getStatusCode() == HttpStatus.OK){
				FixturesResponse body = (FixturesResponse)response.getBody();
				List<Fixtures> fixtures = body.getResponse();
				log.info(fixtures.toString());

				for (Fixtures data : fixtures){
					ZonedDateTime now = ZonedDateTime.now();
					ZonedDateTime fixtureTime = data.getFixture().getDate();
					Duration duration = Duration.between(now, fixtureTime);
					log.info(duration.toString());
					if (duration.toHours() < 24){
						CheerFixture cheerFixture = CheerFixture.builder()
							.fixture(null)
							.homeCount(0)
							.awayCount(0)
							.build();
						cheerFixtureRepository.save(cheerFixture);
					}
				}
			}
		}

	}



}
