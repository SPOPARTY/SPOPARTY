package com.spoparty.batch.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.spoparty.batch.util.FootballApiUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduler {

	private final FootballApiUtil apiRequest;

	List<Long> fixtureIdInProgress = new ArrayList<>();

	// @Scheduled(fixedRate = 10000)
	// public void fixtureLive() {
	//
	// 	System.out.println("실행!");
	//
	// 	MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
	// 	queryParams.add("leauge", "1");
	//
	// 	ResponseEntity<String> request = apiRequest.sendRequest("/league", queryParams);
	//
	// 	System.out.println(request.getBody());
	//
	//
	// }



}
