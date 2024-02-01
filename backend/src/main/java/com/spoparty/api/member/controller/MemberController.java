package com.spoparty.api.member.controller;

import static com.spoparty.api.common.constants.ErrorCode.*;
import static com.spoparty.api.common.constants.SuccessCode.*;

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

import com.spoparty.api.common.dto.ApiResponse;
import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.football.entity.Team;
import com.spoparty.api.member.entity.FollowingTeamProjection;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.entity.MemberProjection;
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
		MemberProjection member = memberService.findByIdProjection(memberId);
		if (member == null)
			throw new CustomException(BAD_CLIENT_REQUEST);
		else
			return ApiResponse.success(GET_SUCCESS, member);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerMember(@RequestBody Member member) {
		log.info("MemberController.register{}: ", member);
		MemberProjection loginMember = memberService.findByLoginIdProjection(member.getLoginId());
		if (loginMember != null)
			throw new CustomException(CONFLICT_DATA);

		member = memberService.registerMember(member);
		return ApiResponse.success(CREATE_SUCCESS, member);
	}

	@PutMapping
	public ResponseEntity<?> modifyMember(@RequestBody Member member) {
		MemberProjection data = memberService.updateMember(member);
		return ApiResponse.success(UPDATE_SUCCESS, data);
	}

	@DeleteMapping("/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable("memberId") Long memberId) {
		memberService.deleteMember(memberId);
		return ApiResponse.success(DELETE_SUCCESS);
	}

	// Follow 기능
	@GetMapping("/{memberId}/follows")
	public ResponseEntity<?> getFollowList(@PathVariable("memberId") Long memberId) {
		List<FollowingTeamProjection> list = memberService.getFollowList(memberId);
		return ApiResponse.success(GET_SUCCESS, list);
	}

	@PostMapping("/follows")
	public ResponseEntity<?> registerFollow(@RequestBody Map<String, Long> data) {
		Long memberId = data.get("memberId");
		Long teamId = data.get("teamId");
		FollowingTeamProjection followingTeam = memberService.registerFollow(memberId, teamId);
		return ApiResponse.success(CREATE_SUCCESS, followingTeam);
	}

	@DeleteMapping("/follows/{followTeamId}")
	public ResponseEntity<?> deleteFollow(@PathVariable("followTeamId") Long followTeamId) {
		memberService.deleteFollow(followTeamId);
		return ApiResponse.success(DELETE_SUCCESS);
	}

	@DeleteMapping("/logout")
	public ResponseEntity<?> logout(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		if (principalDetails == null)
			throw new CustomException(UNAUTHORIZED_USER);
		memberService.deleteToken(principalDetails.getMember().getId());
		return ApiResponse.success(DELETE_SUCCESS);
	}

	@GetMapping("/teams")
	public ResponseEntity<?> getTeamList() {
		List<Team> list = memberService.getTeamList();
		return ApiResponse.success(GET_SUCCESS, list);
	}
}