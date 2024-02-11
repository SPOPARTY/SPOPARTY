package com.spoparty.api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spoparty.api.common.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> customExceptionHandler(CustomException exception, HttpServletRequest request) {
		log.error("ExceptionCode: {}", exception.getCode());
		log.error("ExceptionMessage: {}", exception.getMessage());
		exception.printStackTrace();
		return ApiResponse.error(exception.getCode(), exception.toString());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> runtimeExceptionHandler(RuntimeException exception, HttpServletRequest request) {
		log.error("ExceptionToString: {}", exception.toString());
		log.error("ExceptionMessage: {}", exception.getMessage());
		exception.printStackTrace();
		return ApiResponse.error(HttpStatus.BAD_REQUEST, exception.toString());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unhandledExceptionHandler(Exception exception, HttpServletRequest request) {
		log.error(exception.toString());
		exception.printStackTrace();
		return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.toString());
	}

}
