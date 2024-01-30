package com.spoparty.api.common.exception;

import com.spoparty.api.common.constants.ErrorCode;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException { // 404 Not Found
	private final ErrorCode code;

	public NotFoundException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
