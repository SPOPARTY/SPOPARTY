package com.spoparty.batch.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FootballApiUtil {



	private static String BASE_URL;
	private static String API_KEY;
	private static String API_HOST;



	FootballApiUtil(@Value("${api-key}")String apiKey, @Value("${api-host}")String apiHost, @Value("${base-url}") String baseUrl) {
		this.API_KEY = apiKey;
		this.API_HOST = apiHost;
		this.BASE_URL = baseUrl;
	}



	// RestTemplate apiRequest
	public <T> ResponseEntity<T> sendRequest(String path, MultiValueMap<String, String> queryParams, Class<T> type) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("x-rapidapi-key", API_KEY);
		headers.set("x-rapidapi-host", API_HOST);


		HttpEntity entity = new HttpEntity(headers);



		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + path)
			.queryParams(queryParams);

		System.out.println(builder.toUriString());
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<T> response = restTemplate.exchange(
			builder.toUriString(),
			HttpMethod.GET, // 요청 메서드 변경 가능
			entity,
			type);


		return response;
	}







	// webClient Request
	// Mono, Flux
	//
	// public WebClient getRequest () {
	// 	HttpClient httpClient = HttpClient.create()
	// 			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
	// 			.responseTimeout(Duration.ofMillis(5000))
	// 				.doOnConnected(conn ->
	// 					conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
	// 					.addHandlerLast(new WriteTimeoutHandler(50000, TimeUnit.MILLISECONDS)));
	//
	//
	// 	return WebClient.builder()
	// 		.baseUrl(BASE_URL)
	// 		.defaultHeader("x-rapidapi-key", API_KEY)
	// 		.defaultHeader("x-rapidapi-host", API_HOST)
	// 		.clientConnector(new ReactorClientHttpConnector(httpClient))
	// 		.build();
	//
	//
	//
	//
	// }


}




