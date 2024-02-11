package com.spoparty.batch.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class SpopartyApiUtil {

	private static final String BASE_URL = "https://i10a802.p.ssafy.io/api";

	// RestTemplate apiRequest
	public <T> ResponseEntity<T> sendPostRequest(String path, Object requestObject, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> entity = new HttpEntity<>(requestObject, headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + path);

		System.out.println(builder.toUriString());
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<T> response = restTemplate.exchange(
			builder.toUriString(),
			HttpMethod.POST,
			entity,
			responseType);

		return response;
	}

}
