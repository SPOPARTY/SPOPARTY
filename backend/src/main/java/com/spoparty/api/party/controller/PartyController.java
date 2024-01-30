package com.spoparty.api.party.controller;

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

import com.spoparty.api.common.dto.ApiResponse;
import com.spoparty.api.party.dto.PartyMemberRequestDto;
import com.spoparty.api.party.dto.PartyRequestDto;
import com.spoparty.api.party.repository.projection.PartyMemberProjection;
import com.spoparty.api.party.repository.projection.PartyProjection;
import com.spoparty.api.party.service.PartyServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyController {
	private final PartyServiceImpl partyService;

	@PostMapping()
	public ApiResponse<?> createParty(@RequestBody @Valid PartyRequestDto partyRequestDto) {
		log.debug("파티 생성 API 시작");
		log.debug("요청 - {}", partyRequestDto);
		PartyProjection response = partyService.createParty(partyRequestDto);
		return ApiResponse.success(PARTY_CREATE_SUCCESS, response);
	}

	@GetMapping("/{partyId}")
	public ApiResponse<?> getParty(@PathVariable Long partyId) {
		log.debug("파티 조회 API 시작");
		PartyProjection response = partyService.findParty(partyId);
		return ApiResponse.success(PARTY_READ_SUCCESS, response);
	}

	@PutMapping("/{partyId}")
	public ApiResponse<?> updateParty(@PathVariable Long partyId, @RequestBody @Valid PartyRequestDto partyRequestDto) {
		log.debug("파티 수정 API 시작");
		PartyProjection response = partyService.updateParty(partyId, partyRequestDto);
		return ApiResponse.success(PARTY_UPDATE_SUCCESS, response);
	}

	@DeleteMapping("/{partyId}")
	public ApiResponse<?> delete(@PathVariable Long partyId) {
		log.debug("파티 삭제 API 시작");
		Long response = partyService.deleteParty(partyId);
		return ApiResponse.success(PARTY_DELETE_SUCCESS, response);
	}

	@GetMapping("/{partyId}/participants")
	public ApiResponse<?> getAllParticipants(@PathVariable Long partyId) {
		log.debug("파티원 목록 조회 API 시작");
		List<PartyMemberProjection> response = partyService.getAllPartyMembers(partyId);
		return ApiResponse.success(PARTY_MEMBERS_READ_SUCCESS, response);
	}

	@PostMapping("/{partyId}/participants")
	public ApiResponse<?> createParticipant(@PathVariable Long partyId,
		@RequestBody PartyMemberRequestDto partyMemberRequestDto) {
		log.debug("파티원 추가 API 시작");
		PartyMemberProjection response = partyService.createPartyMember(partyId, partyMemberRequestDto);
		return ApiResponse.success(PARTY_MEMBER_CREATE_SUCCESS, response);
	}

	@PutMapping("/{partyId}/participants/{memberId}")
	public ApiResponse<?> getParticipant(@PathVariable Long partyId, @PathVariable Long memberId) {
		log.debug("파티원 조회 API 시작");
		PartyMemberProjection response = partyService.getPartyMember(partyId, memberId);
		return ApiResponse.success(PARTY_MEMBER_READ_SUCCESS, response);
	}

	@DeleteMapping("/{partyId}/participants/{memberId}")
	public ApiResponse<?> deleteParticipant(@PathVariable Long partyId, @PathVariable Long memberId) {
		log.debug("파티원 삭제 API 시작");
		Long response = partyService.deletePartyMember(partyId, memberId);
		return ApiResponse.success(PARTY_MEMBER_DELETE_SUCCESS, response);
	}
}
