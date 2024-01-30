package com.spoparty.api.common.exception;

import com.spoparty.api.common.constants.ErrorCode;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException { // 403 Forbidden
	private final ErrorCode code;

	public ForbiddenException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
