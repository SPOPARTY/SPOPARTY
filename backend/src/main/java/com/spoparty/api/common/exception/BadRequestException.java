package com.spoparty.api.common.exception;

import com.spoparty.api.common.constants.ErrorCode;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException { // 400 Bad Request
	private final ErrorCode code;

	public BadRequestException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
