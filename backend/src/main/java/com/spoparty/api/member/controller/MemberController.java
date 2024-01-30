package com.spoparty.api.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Team;
import com.spoparty.api.member.entity.FollowingTeam;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.projection.FollowingTeamProjection;
import com.spoparty.api.member.service.MemberService;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	// Member 기능
	@GetMapping("/{memberId}")
	public ResponseEntity<?> getMember(@PathVariable("memberId") Long memberId) {
		Member member = memberService.findById(memberId);
		if (member == null)
			return ResponseEntity.status(404).body(null);

		return ResponseEntity.status(200).body(member);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerMember(@RequestBody Member member) {
		log.info("MemberController.register{}: ", member);
		Member loginMember = memberService.findByLoginId(member.getLoginId());
		if (loginMember != null)
			return ResponseEntity.status(409).body(null);

		member = memberService.registerMember(member);
		return ResponseEntity.status(201).body(member);
	}

	@PutMapping
	public ResponseEntity<?> modifyMember(@RequestBody Member member) {
		member = memberService.updateMember(member);
		if (member == null)
			return ResponseEntity.status(404).body(null);
		return ResponseEntity.status(200).body(member);
	}

	@DeleteMapping("/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable("memberId") Long memberId) {
		Member member = memberService.deleteMember(memberId);
		if (member == null)
			return ResponseEntity.status(400).body(null);
		else
			return ResponseEntity.status(200).body(null);
	}

	// Follow 기능
	@GetMapping("/{memberId}/follows")
	public ResponseEntity<?> getFollowList(@PathVariable("memberId") Long memberId) {
		List<FollowingTeamProjection> list = memberService.getFollowList(memberId);
		if (list.isEmpty())
			return ResponseEntity.status(404).body(null);
		else
			return ResponseEntity.status(200).body(list);
	}

	@PostMapping("/follows")
	public ResponseEntity<?> registerFollow(@RequestBody Map<String, Long> data) {
		Long memberId = data.get("memberId");
		Long teamId = data.get("teamId");
		FollowingTeamProjection followingTeam = memberService.registerFollow(memberId, teamId);
		if (followingTeam == null)
			return ResponseEntity.status(404).body(null);
		else
			return ResponseEntity.status(201).body(followingTeam);
	}

	@DeleteMapping("/follows/{followTeamId}")
	public ResponseEntity<?> deleteFollow(@PathVariable("followTeamId") Long followTeamId) {
		FollowingTeam followingTeam = memberService.deleteFollow(followTeamId);
		if (followingTeam == null)
			return ResponseEntity.status(400).body(null);
		else
			return ResponseEntity.status(200).body(null);
	}

	@DeleteMapping("/logout")
	public ResponseEntity<?> logout(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		memberService.deleteToken(principalDetails.getMember().getId());
		return ResponseEntity.status(200).body(null);
	}

	@GetMapping("/teams")
	public ResponseEntity<?> getTeamList() {
		List<Team> list = memberService.getTeamList();
		if (list.isEmpty())
			return ResponseEntity.status(404).body(null);
		else
			return ResponseEntity.status(200).body(list);

	}
}