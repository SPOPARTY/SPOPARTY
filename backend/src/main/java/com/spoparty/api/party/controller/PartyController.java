package com.spoparty.api.party.controller;

import static com.spoparty.api.common.constants.SuccessCode.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.spoparty.api.member.entity.File;
import com.spoparty.api.member.service.FileService;
import com.spoparty.api.party.dto.response.RecordingResponseDTO;
import com.spoparty.common.util.CustomMultipartFile;
import io.openvidu.java.client.Recording;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
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
import com.spoparty.api.member.service.NotificationService;
import com.spoparty.api.party.dto.request.PartyUpdateRequestDto;
import com.spoparty.api.party.dto.response.PartyResponseDTO;
import com.spoparty.api.party.entity.PartyMemberProjection;
import com.spoparty.api.party.service.PartyServiceImpl;
import com.spoparty.security.model.PrincipalDetails;

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
	private final FileService fileService;

	private final PartyServiceImpl partyService;

	@PostMapping()
	public ResponseEntity<?> createParty(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long clubId) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		log.debug("파티 생성 API 시작");
		log.debug("요청 - principalDetails:{}, clubId: {}", principalDetails, clubId);
		PartyResponseDTO response = partyService.createParty(principalDetails, clubId);
		log.debug("응답 - {}", response);
		return ApiResponse.success(PARTY_CREATE_SUCCESS, response);
	}

	@PostMapping("/{partyId}/participants")
	public ResponseEntity<?> createParticipant(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long clubId, @PathVariable Long partyId) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		log.debug("파티원 추가 API 시작");
		log.debug("요청 - principalDetails: {}, clubId: {}, partyId: {}", principalDetails, clubId, partyId);
		PartyMemberProjection response = partyService.createPartyMember(principalDetails, clubId, partyId);
		log.debug("응답 - {}", response);
		return ApiResponse.success(PARTY_MEMBER_CREATE_SUCCESS, response);
	}

	@GetMapping("/{partyId}")
	public ResponseEntity<?> getParty(@PathVariable Long partyId, @PathVariable Long clubId) {
		log.debug("파티 조회 API 시작");
		log.debug("요청 - partyId: {}, clubId: {}", partyId, clubId);
		PartyResponseDTO response = partyService.findParty(partyId);
		log.debug("응답 - {}", response);
		return ApiResponse.success(PARTY_READ_SUCCESS, response);
	}

	@PutMapping("/{partyId}")
	public ResponseEntity<?> updateParty(@PathVariable Long partyId,
		@RequestBody @Valid PartyUpdateRequestDto partyUpdateRequestDto,
		@PathVariable Long clubId) {
		log.debug("파티 수정 API 시작");
		log.debug("요청 - partyUpdateRequestDto: {}, clubId: {}", partyUpdateRequestDto, clubId);
		PartyResponseDTO response = partyService.updateParty(partyId, partyUpdateRequestDto);
		log.debug("응답 - {}", response);
		return ApiResponse.success(PARTY_UPDATE_SUCCESS, response);
	}

	@DeleteMapping("/{partyId}")
	public ResponseEntity<?> deleteParty(@PathVariable Long partyId, @PathVariable Long clubId) {
		log.debug("파티 삭제 API 시작");
		log.debug("요청 - partyId: {}, clubId: {}", partyId, clubId);
		Long response = partyService.deleteParty(partyId, clubId);
		log.debug("응답 - {}", response);
		return ApiResponse.success(PARTY_DELETE_SUCCESS, response);
	}

	@GetMapping("/{partyId}/participants")
	public ResponseEntity<?> getAllParticipants(@PathVariable Long partyId, @PathVariable Long clubId) {
		log.debug("파티원 목록 조회 API 시작");
		log.debug("요청 - partyId: {}, clubId: {}", partyId, clubId);
		List<PartyMemberProjection> response = partyService.findAllPartyMembers(partyId);
		log.debug("응답 - {}", response);
		return ApiResponse.success(PARTY_MEMBERS_READ_SUCCESS, response);
	}

	@GetMapping("/{partyId}/participants/{participantId}")
	public ResponseEntity<?> getParticipant(@PathVariable Long partyId, @PathVariable Long participantId,
		@PathVariable Long clubId) {
		log.debug("파티원 조회 API 시작");
		log.debug("요청 - partyId: {}, participantId: {}, clubId: {}", partyId, participantId, clubId);
		PartyMemberProjection response = partyService.findPartyMember(participantId, PartyMemberProjection.class);
		log.debug("응답 - {}", response);
		return ApiResponse.success(PARTY_MEMBER_READ_SUCCESS, response);
	}

	@DeleteMapping("/{partyId}/participants/{participantId}")
	public ResponseEntity<?> deleteParticipant(@PathVariable Long partyId, @PathVariable Long participantId,
		@PathVariable Long clubId) {
		log.debug("파티원 삭제 API 시작");
		log.debug("요청 - partyId: {}, participantId: {}, clubId: {}", partyId, participantId, clubId);
		Long response = partyService.deletePartyMember(partyId, participantId, clubId);
		log.debug("응답 - {}", response);
		return ApiResponse.success(PARTY_MEMBER_DELETE_SUCCESS, response);
	}

	@PostMapping("/recording/start")
	public ResponseEntity<?> startRecording(@RequestBody Map<String, Object> params) throws
			OpenViduJavaClientException,
			OpenViduHttpException {
		String sessionId = (String) params.get("session");

		Recording recording = partyService.startRecording(sessionId, params);

		return ApiResponse.success(RECORD_START_REQUEST_SUCCESS, recording);
	}

	@PostMapping("/recording/stop")
	public ResponseEntity<?> stopRecording(@RequestBody Map<String, Object> params) throws
			OpenViduJavaClientException,
			OpenViduHttpException, IOException {
		String recordingId = (String) params.get("recording");

		// 녹화 종료
		partyService.stopRecording(recordingId);

		// 녹화된 영상 S3 업로드, DB 저장
		RecordingResponseDTO recordingResponseDTO = partyService.uploadRecording(recordingId);

		// 서버에 저장된 영상 제거
		partyService.deleteRecording(recordingId);

		return ApiResponse.success(RECORD_STOP_REQUEST_SUCCESS, recordingResponseDTO);
	}
}
