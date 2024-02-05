package com.spoparty.batch.util;

import java.net.URI;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Component
public class ApiRequest {



	private static String BASE_URL;
	private static String API_KEY;
	private static String API_HOST;



	ApiRequest(@Value("${api-key}")String apiKey, @Value("${api-host}")String apiHost, @Value("${base-url}") String baseUrl) {
		this.BASE_URL = apiKey;
		this.API_KEY = apiHost;
		this.API_HOST = baseUrl;
	}
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

	public ResponseEntity<String> sendRequest(String path, MultiValueMap<String, String> queryParams) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("x-rapidapi-key", API_KEY);
		headers.set("x-rapidapi-host", API_HOST);


		HttpEntity entity = new HttpEntity(headers);


		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + path)
			.queryParams(queryParams);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(
			builder.toUriString(),
			HttpMethod.GET, // 요청 메서드 변경 가능
			entity,
			String.class);


		return response;
	}


}

