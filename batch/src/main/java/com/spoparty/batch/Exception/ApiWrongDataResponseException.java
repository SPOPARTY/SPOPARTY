package com.spoparty.batch.Exception;


// 외부 api가 잘못된 데이터를 가져온 경우 발생하는 예외
public class ApiWrongDataResponseException extends RuntimeException{

	public ApiWrongDataResponseException(String message) {
		super(message); // RuntimeException 클래스의 생성자를 호출합니다.
	}
}