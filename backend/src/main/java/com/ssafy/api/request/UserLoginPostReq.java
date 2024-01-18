package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /api/v1/auth/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
public class UserLoginPostReq {
	String id;
	String password;
}
