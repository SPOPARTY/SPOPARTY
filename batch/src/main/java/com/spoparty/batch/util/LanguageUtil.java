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
public class LanguageUtil {

	private static String BASE_URL;
	private static String API_SECRET;
	private static String API_ID;

	public LanguageUtil(@Value("lang-base-url")String baseUrl, @Value("lang-secret") String apiSecret, @Value("lang-id") String apiId){
		BASE_URL = baseUrl;
		API_SECRET = apiSecret;
		API_ID = apiId;
	}

	// RestTemplate apiRequest
	public <T> ResponseEntity<T> sendRequest(String path, MultiValueMap<String, String> queryParams, Class<T> type) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", API_ID);
		headers.set("X-Naver-Client-Secret", API_SECRET);
		headers.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");


		HttpEntity entity = new HttpEntity(headers);



		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL);

		System.out.println(builder.toUriString());
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<T> response = restTemplate.exchange(
			builder.toUriString(),
			HttpMethod.POST, // 요청 메서드 변경 가능
			entity,
			type);


		return response;
	}
}
