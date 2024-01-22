package com.spoparty.api.member.response;

import com.spoparty.api.member.entity.User;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
public class UserRes{
	String userId;
	
	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setUserId(user.getUserId());
		return res;
	}
}
