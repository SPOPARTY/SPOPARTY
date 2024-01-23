package com.spoparty.api.common.dto;

import org.springframework.http.HttpStatus;

import com.spoparty.api.common.constants.ErrorCode;
import com.spoparty.api.common.constants.SuccessCode;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
	private int status;
	private String message;
	private T data;

	private ApiResponse(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	private ApiResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public static <T> ApiResponse<T> success(SuccessCode code, T data) {
		return new ApiResponse<>(code.getStatus().value(), code.getMessage(), data);
	}

	public static <T> ApiResponse<T> error(ErrorCode code) {
		return new ApiResponse<>(code.getStatus().value(), code.getMessage());
	}

	public static <T> ApiResponse<T> error(HttpStatus status, String message) {
		return new ApiResponse<>(status.value(), message);
	}
}
