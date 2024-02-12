package com.spoparty.batch.Exception;

// API에 대한 요청이 실패했을 때 발생하는 예외
public class ApiRequestFailedException extends RuntimeException{
	
	public ApiRequestFailedException(String message) {
		super(message); // RuntimeException 클래스의 생성자를 호출합니다.
	}
}
