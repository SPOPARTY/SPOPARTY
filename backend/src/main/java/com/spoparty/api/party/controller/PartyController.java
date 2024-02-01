package com.spoparty.api.party.controller;

import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.common.dto.ApiResponse;
import com.spoparty.api.party.dto.PartyCreateRequestDto;
import com.spoparty.api.party.dto.PartyMemberRequestDto;
import com.spoparty.api.party.dto.PartyResponseDto;
import com.spoparty.api.party.dto.PartyUpdateRequestDto;
import com.spoparty.api.party.repository.projection.PartyMemberProjection;
import com.spoparty.api.party.service.PartyServiceImpl;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/clubs/{clubId}/party")
public class PartyController {
	private final PartyServiceImpl partyService;

	@PostMapping()
	public ResponseEntity<?> createParty(@RequestBody @Valid PartyCreateRequestDto partyRequestDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		log.debug("파티 생성 API 시작");
		log.debug("요청 - {}", partyRequestDto);
		PartyResponseDto response = partyService.createParty(partyRequestDto);
		return ApiResponse.success(PARTY_CREATE_SUCCESS, response);
	}

	@GetMapping("/{partyId}")
	public ResponseEntity<?> getParty(@PathVariable Long partyId, @PathVariable String clubId) {
		log.debug("파티 조회 API 시작");
		PartyResponseDto response = partyService.findParty(partyId);
		return ApiResponse.success(PARTY_READ_SUCCESS, response);
	}

	@PutMapping("/{partyId}")
	public ResponseEntity<?> updateParty(@PathVariable Long partyId, @RequestBody @Valid PartyUpdateRequestDto partyUpdateRequestDto,
		@PathVariable String clubId) {
		log.debug("파티 수정 API 시작");
		PartyResponseDto response = partyService.updateParty(partyId, partyUpdateRequestDto);
		return ApiResponse.success(PARTY_UPDATE_SUCCESS, response);
	}

	@DeleteMapping("/{partyId}")
	public ResponseEntity<?> delete(@PathVariable Long partyId, @PathVariable Long clubId) {
		log.debug("파티 삭제 API 시작");
		Long response = partyService.deleteParty(partyId, clubId);
		return ApiResponse.success(PARTY_DELETE_SUCCESS, response);
	}

	@GetMapping("/{partyId}/participants")
	public ResponseEntity<?> getAllParticipants(@PathVariable Long partyId, @PathVariable String clubId) {
		log.debug("파티원 목록 조회 API 시작");
		List<PartyMemberProjection> response = partyService.findAllPartyMembers(partyId);
		return ApiResponse.success(PARTY_MEMBERS_READ_SUCCESS, response);
	}

	@PostMapping("/{partyId}/participants")
	public ResponseEntity<?> createParticipant(@PathVariable Long partyId,
		@RequestBody PartyMemberRequestDto partyMemberRequestDto, @PathVariable String clubId) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		log.debug("파티원 추가 API 시작");
		PartyMemberProjection response = partyService.createPartyMember(partyId, partyMemberRequestDto);
		return ApiResponse.success(PARTY_MEMBER_CREATE_SUCCESS, response);
	}

	@GetMapping("/{partyId}/participants/{participantId}")
	public ResponseEntity<?> getParticipant(@PathVariable Long partyId, @PathVariable Long participantId,
		@PathVariable String clubId) {
		log.debug("파티원 조회 API 시작");
		PartyMemberProjection response = partyService.findPartyMember(participantId, PartyMemberProjection.class);
		return ApiResponse.success(PARTY_MEMBER_READ_SUCCESS, response);
	}

	@DeleteMapping("/{partyId}/participants/{participantId}")
	public ResponseEntity<?> deleteParticipant(@PathVariable Long partyId, @PathVariable Long participantId,
		@PathVariable String clubId) {
		log.debug("파티원 삭제 API 시작");
		Long response = partyService.deletePartyMember(participantId);
		return ApiResponse.success(PARTY_MEMBER_DELETE_SUCCESS, response);
	}
}
