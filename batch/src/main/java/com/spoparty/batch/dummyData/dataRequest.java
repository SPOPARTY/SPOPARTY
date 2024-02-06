package com.spoparty.batch.dummyData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.spoparty.batch.entity.Season;
import com.spoparty.batch.repository.SeasonRepository;
import com.spoparty.batch.util.FootballApiUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class dataRequest {

	public final FootballApiUtil apiRequest;
	public final SeasonRepository seasonRepository;

	// @Transactional(value = defaultTxManager())
	// @Scheduled(fixedRate=20000)
	void getAllSeason() {
		ResponseEntity<Map> response = apiRequest.sendRequest("/leagues/seasons", null, Map.class);
		List<Integer> seasons = (List<Integer>)response.getBody().get("response");

		List<Season> seasonEntitys = new ArrayList<>();
		for (Integer season : seasons) {
			seasonEntitys.add(Season.builder()
				.value(Integer.toString(season))
				.build());

		}

		seasonRepository.saveAll(seasonEntitys);
	}


	// @Scheduled(fixedRate=20000)
	void getAllLeague() {
		List<Long> leagueIds = new ArrayList<>(Arrays.asList((long)1, (long)7, (long)8, (long)18,
			(long)15, (long)532, (long)965, (long)1012, (long)17, (long)292, (long)293, (long)295, (long)294,
			(long)48, (long)143, (long)556, (long)78, (long)529, (long)81, (long)66, (long)526, (long)119));

		for (Long id : leagueIds) {
			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>() {
			};

			queryParams.add("id", Long.toString(id));

			ResponseEntity<Map> response = apiRequest.sendRequest("/leagues", queryParams, Map.class);
			List<Map<String, Object>> data = (List)response.getBody().get("response");

			Map<String, String> league = (Map)data.get(0).get("league");

			Map<String, String> country = (Map)data.get(0).get("country");

			List<Map<String, String>> seasons = (List)data.get(0).get("seasons");
			System.out.println(league);
			System.out.println(country);
			System.out.println(seasons);

		}
	}



}
