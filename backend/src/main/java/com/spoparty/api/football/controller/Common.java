package com.spoparty.api.football.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import com.spoparty.api.football.response.ResponseDTO;

@Component
public class Common {

	public HttpStatusCode getStatusByContent(ResponseDTO responseDTO) {
		if (responseDTO.getData() == null) {
			return HttpStatusCode.valueOf(404);
		} else {
			return HttpStatusCode.valueOf(200);
		}
	}
}
