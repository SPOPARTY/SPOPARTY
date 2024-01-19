package com.spoparty.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.request.UserRegisterPostReq;
import com.spoparty.api.response.UserRes;
import com.spoparty.api.service.UserService;
import com.spoparty.common.auth.SsafyUserDetails;
import com.spoparty.common.model.response.BaseResponseBody;
import com.spoparty.db.entity.User;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping()
	public ResponseEntity<? extends BaseResponseBody> register(
		@RequestBody UserRegisterPostReq registerInfo) {

		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		User user = userService.createUser(registerInfo);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@GetMapping("/me")
	public ResponseEntity<UserRes> getUserInfo(Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);

		return ResponseEntity.status(200).body(UserRes.of(user));
	}

	@GetMapping("/mmm")
	public String mmm() {
		System.out.println("zxczxzcczxzxczx");
		return "mmm";
	}

}
