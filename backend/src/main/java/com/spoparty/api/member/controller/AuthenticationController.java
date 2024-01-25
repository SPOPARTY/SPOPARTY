package com.spoparty.api.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.service.EmailService;
import com.spoparty.api.member.service.MemberService;
import com.spoparty.common.util.JwtTokenUtil;
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
	private final JwtTokenUtil jwtTokenUtil;

	@GetMapping("/email-check/{email}")
	public ResponseEntity<?> emailCheck(@PathVariable("email") String email) throws InterruptedException {
		Member member = memberService.findByEmail(email);
		if (member != null)
			return ResponseEntity.status(409).body(null);

		emailService.sendEmail(email);
		return ResponseEntity.status(200).body(null);
	}

	@PostMapping("/email-check")
	public ResponseEntity<?> emailCheck(@RequestBody Map<String, String> data) throws InterruptedException {
		String email = data.get("email");
		String code = data.get("code");
		log.debug(data.toString());
		if (emailService.checkCode(email, Integer.parseInt(code)))
			return ResponseEntity.status(200).body(null);
		else
			return ResponseEntity.status(400).body(null);
	}

	@GetMapping("/id-check/{loginId}")
	public ResponseEntity<?> idCheck(@PathVariable("loginId") String loginId) {
		Member member = memberService.findByLoginId(loginId);
		if (member == null)
			return ResponseEntity.status(200).body(null);
		else
			return ResponseEntity.status(409).body(null);
	}

	@RequestMapping("/token")
	public ResponseEntity<?> generateToken(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Long id = principalDetails.getMember().getId();
		String accessToken = jwtTokenUtil.createAccessToken(id + "");
		String refreshToken = jwtTokenUtil.createRefreshToken();
		log.info("AuthenticationController.generateToken(): ");
		log.info("accessToken : {}", accessToken);
		log.info("refreshToken : {}", refreshToken);
		return ResponseEntity
			.status(200)
			.header("accessToken", accessToken)
			.header("refreshToken", refreshToken)
			.body(null);
	}

}
