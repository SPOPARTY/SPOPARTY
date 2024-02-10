package com.spoparty.api.club.controller;

import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.List;

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

import com.spoparty.api.club.dto.request.ClubRequestDTO;
import com.spoparty.api.club.dto.request.InviteRequestDTO;
import com.spoparty.api.club.dto.response.ClubMemberResponseDTO;
import com.spoparty.api.club.dto.response.ClubResponseDTO;
import com.spoparty.api.club.dto.response.InviteResponseDTO;
import com.spoparty.api.club.service.ClubMemberServiceImpl;
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
	private final ClubMemberServiceImpl clubMemberService;

	@GetMapping("/recent")
	public ResponseEntity<?> getRecentClubs(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.debug("최근 활동 그룹 목록 조회 API 시작");
		log.debug("요청 principalDetails - {}", principalDetails);
		List<ClubResponseDTO> response = clubService.findRecentClubs(principalDetails);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(GET_SUCCESS, response);
	}

	@PostMapping
	public ResponseEntity<?> createClub(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid ClubRequestDTO clubRequestDTO) {
		log.debug("그룹 생성 API 시작");
		log.debug("요청 - principalDetails: {}, clubRequestDTO: {}", principalDetails, clubRequestDTO);
		ClubResponseDTO response = clubService.createClub(principalDetails, clubRequestDTO);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(CLUB_CREATE_SUCCESS, response);
	}

	@GetMapping("/{clubId}")
	public ResponseEntity<?> getClub(@PathVariable Long clubId) {
		log.debug("그룹 조회 API 시작");
		log.debug("요청 clubId - {}", clubId);
		ClubResponseDTO response = clubService.findClub(clubId);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(CLUB_READ_SUCCESS, response);
	}

	@PutMapping("/{clubId}")
	public ResponseEntity<?> updateClubName(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long clubId, @RequestBody @Valid ClubRequestDTO clubRequestDTO) {
		log.debug("그룹명 수정 API 시작");
		log.debug("요청 - principalDetails: {}, clubId: {}, clubRequestDTO: {}", principalDetails, clubId, clubRequestDTO);
		ClubResponseDTO response = clubService.updateClubName(principalDetails, clubId, clubRequestDTO);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(CLUB_UPDATE_SUCCESS, response);
	}

	@DeleteMapping("/{clubId}")
	public ResponseEntity<?> deleteClub(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long clubId) {
		log.debug("그룹 삭제 API 시작");
		log.debug("요청 principalDetails - {}", principalDetails);
		Long response = clubService.deleteClub(principalDetails, clubId); // 삭제된 clubId 반환
		log.debug("응답 response - {}", response);
		return ApiResponse.success(CLUB_DELETE_SUCCESS, response);
	}

	@GetMapping("/invite/{clubId}")
	public ResponseEntity<?> getInviteUrl(@PathVariable Long clubId) {
		log.debug("초대 링크 반환 API 시작");
		log.debug("요청 clubId - {}", clubId);
		InviteResponseDTO response = clubMemberService.getInviteUrl(clubId);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(INVITE_URL_GET_SUCCESS, response);
	}

	@PostMapping("/invite")
	public ResponseEntity<?> joinClub(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid InviteRequestDTO inviteRequestDTO) {
		log.debug("그룹원 추가 API 시작");
		log.debug("요청 - principalDetails: {}, InviteRequestDTO: {}", principalDetails, inviteRequestDTO);
		ClubMemberResponseDTO response = clubMemberService.createClubMember(principalDetails, inviteRequestDTO);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(CLUB_MEMBER_CREATE_SUCCESS, response);
	}

	@GetMapping("/{clubId}/clubmembers")
	public ResponseEntity<?> getAllClubMembers(@PathVariable Long clubId) {
		log.debug("그룹원 조회 API 시작");
		log.debug("요청 clubId - {}", clubId);
		List<ClubMemberResponseDTO> response = clubMemberService.findClubMembers(clubId);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(CLUB_MEMBERS_READ_SUCCESS, response);
	}

	@PutMapping("/{clubId}/host/{clubMemberId}")
	public ResponseEntity<?> assignHost(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long clubId, @PathVariable Long clubMemberId) {
		log.debug("그룹장 수정 API 시작");
		log.debug("요청 principalDetails: {}, clubId: {}, clubMemberId - {}", principalDetails, clubId, clubMemberId);
		ClubMemberResponseDTO response = clubMemberService.assignHost(principalDetails, clubId, clubMemberId);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(CLUB_HOST_UPDATE_SUCCESS, response);
	}

	@DeleteMapping("/{clubId}/clubmembers")
	public ResponseEntity<?> leaveClub(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long clubId) {
		log.debug("그룹원 삭제 API 시작");
		log.debug("요청 principalDetails: {}, clubId: {}", principalDetails, clubId);
		Long response = clubMemberService.deleteClubMember(principalDetails, clubId);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(CLUB_MEMBER_DELETE_SUCCESS, response);
	}

	@DeleteMapping("/{clubId}/clubmembers/{clubMemberId}")
	public ResponseEntity<?> expelClubMember(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long clubId, @PathVariable Long clubMemberId) {
		log.debug("그룹원 강퇴 API 시작");
		log.debug("요청 principalDetails: {}, clubId: {}, clubMemberId - {}", principalDetails, clubId, clubMemberId);
		Long response = clubMemberService.deleteClubMemberByHost(principalDetails, clubId, clubMemberId);
		log.debug("응답 response - {}", response);
		return ApiResponse.success(CLUB_MEMBER_DELETE_SUCCESS, response);
	}
}