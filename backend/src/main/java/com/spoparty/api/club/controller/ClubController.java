package com.spoparty.api.club.controller;

import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.spoparty.api.club.dto.ClubResponseDto;
import com.spoparty.api.club.dto.InviteRequestDto;
import com.spoparty.api.club.dto.InviteResponseDto;
import com.spoparty.api.club.service.ClubServiceImpl;
import com.spoparty.api.common.dto.ApiResponse;
import com.spoparty.security.model.PrincipalDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/clubs")
public class ClubController {
	private final ClubServiceImpl clubService;

	@GetMapping("/recent")
	public ApiResponse<List<ClubResponseDto>> getRecentClubs(
		@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.debug("최근 활동 그룹 목록 조회 API 시작");
		log.debug("member 정보 - {}", principalDetails.getMember());
		List<ClubResponseDto> response = clubService.findRecentClubs(principalDetails.getMember());
		return ApiResponse.success(GET_SUCCESS, response);
	}

	@PostMapping
	public ApiResponse<ClubResponseDto> create(@RequestBody @Valid ClubRequestDto clubRequestDto) {
		log.debug("그룹 생성 API 시작");
		log.debug("ClubRequestDto 정보 - {}", clubRequestDto);
		ClubResponseDto response = clubService.createClub(clubRequestDto);
		return ApiResponse.success(CLUB_CREATE_SUCCESS, response);
	}

	@GetMapping("/{clubId}")
	public ApiResponse<ClubResponseDto> getClub(@PathVariable Long clubId) {
		log.debug("그룹 조회 API 시작");
		ClubResponseDto response = clubService.findClub(clubId);
		return ApiResponse.success(CLUB_READ_SUCCESS, response);
	}

	@PutMapping("/{clubId}")
	public ApiResponse<ClubResponseDto> update(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@PathVariable Long clubId, @RequestBody @Valid ClubRequestDto clubRequestDto) {
		log.debug("그룹명 수정 API 시작");
		ClubResponseDto response = clubService.updateClubName(principalDetails.getMember(), clubId, clubRequestDto);
		return ApiResponse.success(CLUB_UPDATE_SUCCESS, response);
	}

	@DeleteMapping("/{clubId}")
	public ApiResponse<Long> delete(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@PathVariable Long clubId) {
		log.debug("그룹 삭제 API 시작");
		Long response = clubService.deleteClub(principalDetails.getMember(), clubId);
		return ApiResponse.success(CLUB_DELETE_SUCCESS, response);
	}

	@GetMapping("/invite/{clubId}")
	public ApiResponse<InviteResponseDto> getInviteUrl(@PathVariable Long clubId) {
		log.debug("초대 링크 반환 API 시작");
		InviteResponseDto response = clubService.getInviteUrl(clubId);
		return ApiResponse.success(INVITE_URL_GET_SUCCESS, response);
	}

	@PostMapping("/invite")
	public ApiResponse<ClubMemberResponseDto> joinGroup(@RequestBody @Valid InviteRequestDto inviteRequestDto) {
		log.debug("그룹원 추가 API 시작");
		ClubMemberResponseDto response = clubService.createGroupMember(inviteRequestDto);
		return ApiResponse.success(CLUB_MEMBER_CREATE_SUCCESS, response);
	}

	@GetMapping("/{clubId}/participants")
	public ApiResponse<List<ClubMemberResponseDto>> getAllGroupMembers(@PathVariable Long clubId) {
		log.debug("그룹원 조회 API 시작");
		List<ClubMemberResponseDto> response = clubService.getGroupMembers(clubId);
		return ApiResponse.success(CLUB_MEMBERS_READ_SUCCESS, response);
	}

	@PutMapping("/{clubId}/host")
	public ApiResponse<ClubMemberResponseDto> assignHost(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@PathVariable Long clubId, @RequestBody @Valid ClubHostRequestDto clubHostRequestDto) {
		log.debug("그룹장 수정 API 시작");
		log.debug("member - {}", principalDetails.getMember());
		ClubMemberResponseDto response = clubService.assignHost(principalDetails.getMember(), clubId,
			clubHostRequestDto);
		return ApiResponse.success(CLUB_HOST_UPDATE_SUCCESS, response);
	}

	@DeleteMapping("/{clubId}/participants")
	public ApiResponse<Long> leaveGroup(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@PathVariable Long clubId) {
		log.debug("그룹원 삭제 API 시작");
		Long response = clubService.deleteGroupMember(principalDetails.getMember(), clubId);
		return ApiResponse.success(CLUB_MEMBER_DELETE_SUCCESS, response);
	}
}