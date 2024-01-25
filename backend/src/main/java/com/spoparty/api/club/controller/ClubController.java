package com.spoparty.api.club.controller;

import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.club.dto.ClubHostRequestDto;
import com.spoparty.api.club.dto.ClubMemberResponseDto;
import com.spoparty.api.club.dto.ClubRequestDto;
import com.spoparty.api.club.dto.InviteRequestDto;
import com.spoparty.api.club.dto.InviteResponseDto;
import com.spoparty.api.club.service.ClubService;
import com.spoparty.api.common.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/clubs")
public class ClubController {
	private final ClubService clubService;

	@PostMapping
	public ApiResponse<Long> create(@RequestBody @Valid ClubRequestDto clubRequestDto) {
		// 회원 검증 로직 필요
		Long response = clubService.createClub(clubRequestDto);
		return ApiResponse.success(CLUB_CREATE_SUCCESS, response);
	}

	@GetMapping("/recent/{memberId}")
	public ApiResponse<List<ClubRequestDto>> getRecentClubs(@PathVariable long memberId) {
		List<ClubRequestDto> response = clubService.findRecentClubs(memberId);
		return ApiResponse.success(GET_SUCCESS, response);
	}

	@PutMapping("/{clubId}")
	public ApiResponse<Boolean> update(@PathVariable long clubId, @RequestBody @Valid ClubRequestDto clubRequestDto) {
		Boolean response = clubService.updateClubName(clubId, clubRequestDto);
		return ApiResponse.success(CLUB_UPDATE_SUCCESS, response);
	}

	@DeleteMapping("/{clubId}")
	public ApiResponse<Boolean> delete(@PathVariable long clubId) {
		// 그룹장 권한 검증 로직 추가 필요
		Boolean response = clubService.deleteClub(clubId);
		return ApiResponse.success(CLUB_DELETE_SUCCESS, response);
	}

	@GetMapping("/{clubId}/invite")
	public ApiResponse<InviteResponseDto> getInviteUrl(@PathVariable long clubId) {
		InviteResponseDto response = clubService.getInviteUrl(clubId);
		return ApiResponse.success(CLUB_DELETE_SUCCESS, response);
	}

	@GetMapping("/{clubId}/participants")
	public ApiResponse<List<ClubMemberResponseDto>> getAllGroupMembers(@PathVariable long clubId) {
		List<ClubMemberResponseDto> response = clubService.getGroupMembers(clubId);
		return ApiResponse.success(CLUB_MEMBERS_READ_SUCCESS, response);
	}

	@PostMapping("/{clubId}/participants")
	public ApiResponse<Boolean> joinGroup(@PathVariable long clubId, @RequestBody @Valid
		InviteRequestDto inviteRequestDto) {
		// 초대 링크 검증 로직 추가 필요
		Boolean response = clubService.createGroupMember(clubId, inviteRequestDto);
		return ApiResponse.success(CLUB_MEMBER_CREATE_SUCCESS, response);
	}

	@PutMapping("/{clubId}/host")
	public ApiResponse<Boolean> assignHost(@PathVariable long clubId, @RequestBody @Valid
		ClubHostRequestDto clubHostRequestDto) {
		//그룹장 권한 검증 로직 필요
		Boolean response = clubService.assignHost(clubId, clubHostRequestDto);
		return ApiResponse.success(CLUB_HOST_UPDATE_SUCCESS, response);
	}

	@DeleteMapping("/{clubId}/participants/{participantId}")
	public ApiResponse<Boolean> leaveGroup(@PathVariable long clubId, @PathVariable("participantId") long memberId) {
		Boolean response = clubService.deleteGroupMember(clubId, memberId);
		return ApiResponse.success(CLUB_MEMBER_DELETE_SUCCESS, response);
	}
}