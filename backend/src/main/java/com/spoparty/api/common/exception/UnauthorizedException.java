package com.spoparty.api.common.exception;

import com.spoparty.api.common.constants.ErrorCode;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException { // 401 Unauthorized
	private final ErrorCode code;

	public UnauthorizedException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
