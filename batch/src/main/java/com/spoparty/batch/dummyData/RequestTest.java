package com.spoparty.batch.dummyData;

import org.springframework.stereotype.Component;

import com.spoparty.batch.util.FootballApiUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestTest {

	private final FootballApiUtil apiRequest;

	// @Scheduled(fixedRate = 10000)
	// public void requestTest() {
	// 	MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
	// 	queryParams.add("league", "48");
	// 	queryParams.add("season", "2023");
	//
	// 	ResponseEntity<Map> response= apiRequest.sendRequest("/fixtures", queryParams, Map.class);
	//
	// 	System.out.println(response);
	// 	System.out.println(response.getBody().getClass());
	// 	System.out.println(response.getBody().get("results"));
	// 	System.out.println(response.getBody().get("response"));
	// 	List<Map<String, Map<String, String>>> a = (List<Map<String, Map<String, String>>>)response.getBody().get("response");
	// 	System.out.println(a);
	// 	System.out.println(a.get(0));
	// 	System.out.println(a.get(0).get("fixture").get("date"));
	//
	//
	// }
}