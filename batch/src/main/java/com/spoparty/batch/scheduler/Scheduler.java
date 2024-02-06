package com.spoparty.batch.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.spoparty.batch.entity.Fixture;
import com.spoparty.batch.util.ApiRequest;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class Scheduler {

	private final ApiRequest apiRequest;

	List<Long> fixtureIdInProgress = new ArrayList<>();

	@Scheduled(fixedRate = 10000)
	public void fixtureLive() {

		System.out.println("실행!");

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("leauge", "1");

		ResponseEntity<String> request = apiRequest.sendRequest("/league", queryParams);

		System.out.println(request.getBody());


	}



}
