package com.spoparty.api.member.controller;

import static com.spoparty.api.common.constants.ErrorCode.*;
import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.common.dto.ApiResponse;
import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.entity.MemberProjection;
import com.spoparty.api.member.service.EmailService;
import com.spoparty.api.member.service.MemberService;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	private final EmailService emailService;
	private final MemberService memberService;

	@GetMapping("/email-check/{email}")
	public ResponseEntity<?> emailCheck(@PathVariable("email") String email) throws Exception {
		Member member = memberService.findByEmail(email);
		if (member != null)
			throw new CustomException(CONFLICT_DATA);
		emailService.sendEmailCode(email);
		return ApiResponse.success(GET_SUCCESS);
	}

	@PostMapping("/email-check")
	public ResponseEntity<?> emailCheck(@RequestBody Map<String, String> data) {
		String email = data.get("email");
		String code = data.get("code");
		log.debug(data.toString());
		if (emailService.checkCode(email, Integer.parseInt(code)))
			return ApiResponse.success(GET_SUCCESS);
		else
			return ApiResponse.error(BAD_CLIENT_REQUEST);
	}

	@GetMapping("/id-check/{loginId}")
	public ResponseEntity<?> idCheck(@PathVariable("loginId") String loginId) {
		MemberProjection member = memberService.findByLoginIdProjection(loginId);
		if (member == null)
			return ApiResponse.success(GET_SUCCESS);
		else
			return ApiResponse.error(CONFLICT_DATA);
	}

	@RequestMapping("/token")
	public ResponseEntity<?> generateToken(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		if (principalDetails == null)
			throw new CustomException(UNAUTHORIZED_USER);
		Long id = principalDetails.getMember().getId();
		String accessToken = memberService.generateAccessToken(id);
		String refreshToken = memberService.generateRefreshToken(id);
		log.info("AuthenticationController.generateToken(): ");
		log.info("accessToken : {}", accessToken);
		log.info("refreshToken : {}", refreshToken);
		Map<String, String> header = new HashMap<>();
		header.put("accessToken", accessToken);
		header.put("refreshToken", refreshToken);
		return ApiResponse.success(GET_SUCCESS, null, header);
	}

	@PostMapping("/regenerate")
	public ResponseEntity<?> regenerateToken(@RequestHeader("Authorization") String accessToken,
		@RequestBody Map<String, String> map) {
		String refreshToken = map.get("refreshToken");
		log.info("accessToken : {}", accessToken);
		log.info("refreshToken : {}", refreshToken);
		accessToken = memberService.regenerateToken(accessToken, refreshToken);
		Map<String, String> header = new HashMap<>();
		header.put("accessToken", accessToken);
		return ApiResponse.success(GET_SUCCESS, null, header);

	}

	@PostMapping("/password")
	public ResponseEntity<?> tempPwd(@RequestBody Member member) throws Exception {
		memberService.tempPwd(member);
		return ApiResponse.success(GET_SUCCESS, null);
	}

}
