package com.spoparty.api.common.exception;

import com.spoparty.api.common.constants.ErrorCode;

import lombok.Getter;

@Getter
public class NumberFormatException extends RuntimeException {
	private final ErrorCode code;

	public NumberFormatException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}