package com.spoparty.api.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spoparty.api.common.constants.ErrorCode;
import com.spoparty.api.common.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	// Custom Bad Request Error
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<?> handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
		logInfo(request, exception.getCode().getStatus(), exception.getCode().getMessage());
		return ApiResponse.error(exception.getCode());
	}

	// Custom Unauthorized Error
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<?> handleUnauthorizedException(UnauthorizedException exception,
		HttpServletRequest request) {
		logInfo(request, exception.getCode().getStatus(), exception.getCode().getMessage());
		return ApiResponse.error(exception.getCode());
	}

	// Custom Internal Server Error
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(InternalServerErrorException.class)
	protected ResponseEntity<?> handleInternalServerErrorException(InternalServerErrorException exception,
		HttpServletRequest request) {
		logInfo(request, exception.getCode().getStatus(), exception.getCode().getMessage());
		return ApiResponse.error(exception.getCode());
	}

	// @RequestBody valid 에러
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<?> handleMethodArgNotValidException(MethodArgumentNotValidException exception,
		HttpServletRequest request) {
		String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		logInfo(request, HttpStatus.BAD_REQUEST, message);
		return ApiResponse.error(HttpStatus.BAD_REQUEST, message);
	}

	// @ModelAttribute valid 에러
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	protected ResponseEntity<?> handleMethodArgNotValidException(BindException exception,
		HttpServletRequest request) {
		String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		logInfo(request, HttpStatus.BAD_REQUEST, message);
		return ApiResponse.error(HttpStatus.BAD_REQUEST, message);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception,
		HttpServletRequest request) {
		logInfo(request, HttpStatus.NOT_FOUND, exception.getMessage());
		return ApiResponse.error(ErrorCode.USER_NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
		logInfo(request, exception.getCode().getStatus(), exception.getMessage());
		return ApiResponse.error(exception.getCode());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DuplicateException.class)
	public ResponseEntity<?> handleDuplicationException(DuplicateException exception, HttpServletRequest request) {
		logInfo(request, exception.getCode().getStatus(), exception.getMessage());
		return ApiResponse.error(exception.getCode());
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<?> handlerForbiddenException(ForbiddenException exception, HttpServletRequest request) {
		logInfo(request, exception.getCode().getStatus(), exception.getMessage());
		return ApiResponse.error(exception.getCode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unhandledExceptionHandler(Exception exception, HttpServletRequest request) {
		log.error(exception.toString());
		return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.toString());
	}

	private void logInfo(HttpServletRequest request, HttpStatus status, String message) {
		log.info("{} {} : {} - {} (traceId: {})",
			request.getMethod(), request.getRequestURI(), status, message, getTraceId());
	}

	private void logWarn(HttpServletRequest request, Exception exception) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		exception.printStackTrace(printWriter);
		String stackTrace = stringWriter.toString();

		log.warn("{} {} (traceId: {})\n{}", request.getMethod(), request.getRequestURI(), getTraceId(), stackTrace);
	}

	private String getTraceId() {
		return MDC.get("traceId");
	}
}
