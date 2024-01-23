package com.spoparty.api.member.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.member.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authentication")
@RestController
public class AuthenticationController {

	private final EmailService emailService;

	@GetMapping("/email-check/{email}")
	public String emailCheck(@PathVariable("email") String email) throws InterruptedException {
		emailService.sendEmail(email);
		return "인증번호가 발송되었습니다.";
	}

	@PostMapping("/email-check")
	public String emailCheck(@RequestBody Map<String, String> data) throws InterruptedException {
		String email = data.get("email");
		String code = data.get("code");
		log.debug(data.toString());
		boolean result = emailService.checkCode(email, Integer.parseInt(code));
		if (result) {
			return "인증성공!";
		}
		return "인증실패ㅠㅠ";
	}

}
