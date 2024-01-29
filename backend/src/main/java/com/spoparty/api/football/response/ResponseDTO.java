package com.spoparty.api.football.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ResponseDTO {

	private String message;

	private Object data;

	public void changeMessage(String message) {
		this.message = message;
	}

	public static ResponseDTO toDTO(Object data, String message) {
		return ResponseDTO.builder()
			.message(message)
			.data(data)
			.build();
	}

}
