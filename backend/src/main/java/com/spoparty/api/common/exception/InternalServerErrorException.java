package com.spoparty.api.common.exception;

import com.spoparty.api.common.constants.ErrorCode;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException { // 500 Internal Server Error

	private final ErrorCode code;

	public InternalServerErrorException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
