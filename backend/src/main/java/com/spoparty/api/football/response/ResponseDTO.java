package com.spoparty.api.football.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ResponseDTO<T> {

	private String message;

	private T data;

	public static ResponseDTO toDTO(Object data, String message) {
		return ResponseDTO.builder()
			.message(message)
			.data(data)
			.build();
	}

}
