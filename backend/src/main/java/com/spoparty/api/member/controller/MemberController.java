package com.spoparty.api.member.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/user")
	public String user() {
		return "user";
	}

	@PostMapping("/join")
	public String join(@RequestBody Member member) {
		Member LonginMember = memberRepository.findByLoginId(member.getLoginId());
		if (LonginMember != null)
			return "이미 가입된 사용자 입니다.";
		member.setLoginPwd(bCryptPasswordEncoder.encode(member.getLoginPwd()));
		member.setRoleName("ROLE_USER");
		member.setStatus(0);
		member.setProvider("basic");
		memberRepository.save(member);
		return "회원가입이 완료되었습니다." + member.toString();
	}

}
