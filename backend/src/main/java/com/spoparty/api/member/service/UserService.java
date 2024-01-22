package com.spoparty.api.member.service;

import com.spoparty.api.member.request.UserRegisterPostReq;
import com.spoparty.api.member.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);
}
