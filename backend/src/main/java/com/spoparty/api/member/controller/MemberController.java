package com.spoparty.api.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/{memberId}")
	public ResponseEntity<?> getMember(@PathVariable("memberId") Long memberId) {
		Member member = memberService.findById(memberId);
		if (member == null)
			return ResponseEntity.status(404).body(null);

		return ResponseEntity.status(200).body(member);
	}

	@PostMapping
	public ResponseEntity<?> registerMember(@RequestBody Member member) {
		log.info("MemberController.register{}: ", member);
		Member loginMember = memberService.findByLoginId(member.getLoginId());
		if (loginMember != null)
			return ResponseEntity.status(409).body(null);

		member = memberService.register(member);
		return ResponseEntity.status(201).body(member);
	}

	@PutMapping
	public ResponseEntity<?> modifyMember(@RequestBody Member member) {
		member = memberService.update(member);
		if (member == null)
			return ResponseEntity.status(404).body(null);
		return ResponseEntity.status(200).body(member);
	}

	@DeleteMapping("/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable("memberId") Long memberId) {
		memberService.delete(memberId);
		return ResponseEntity.status(200).body(null);
	}

}
