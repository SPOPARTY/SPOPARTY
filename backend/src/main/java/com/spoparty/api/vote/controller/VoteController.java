package com.spoparty.api.vote.controller;

import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.common.dto.ApiResponse;
import com.spoparty.api.vote.domain.Vote;
import com.spoparty.api.vote.dto.VoteCreateRequestDTO;
import com.spoparty.api.vote.dto.VoteUpdateRequestDTO;
import com.spoparty.api.vote.service.VoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/party/{partyId}/votes")
public class VoteController {
	private final VoteService voteService;

	@PostMapping
	public ResponseEntity<?> create(@PathVariable String partyId,
		@RequestBody @Valid VoteCreateRequestDTO voteRequestDTO) {
		log.debug("투표 생성 시작");
		log.debug("partyId - {}, VoteCreateRequestDTO - {}", partyId, voteRequestDTO);
		Vote response = voteService.create(partyId, voteRequestDTO);
		return ApiResponse.success(VOTE_CREATE_SUCCESS, response);
	}

	@GetMapping("/{voteId}")
	public ResponseEntity<?> getVote(@PathVariable String voteId) {
		log.debug("투표 조회 시작");
		log.debug("voteId - {}", voteId);
		Vote response = voteService.getVote(voteId);
		return ApiResponse.success(VOTE_READ_SUCCESS, response);
	}

	@PutMapping("/{voteId}")
	public ResponseEntity<?> vote(@PathVariable String voteId,
		@RequestBody @Valid VoteUpdateRequestDTO voteRequestDTO) {
		log.debug("투표 수정 시작");
		log.debug("voteId - {}, VoteUpdateRequestDTO - {}", voteId, voteRequestDTO);
		Vote response = voteService.update(voteId, voteRequestDTO);
		return ApiResponse.success(VOTE_UPDATE_SUCCESS, response);
	}

	@GetMapping("/ongoing")
	public ResponseEntity<?> getOngoingVotes(@PathVariable String partyId) {
		log.debug("진행 중 투표 목록 조회 시작");
		log.debug("partyId - {}", partyId);
		List<Vote> response = voteService.getVotesByOngoingStatus(partyId, 1);
		return ApiResponse.success(VOTES_READ_SUCCESS, response);
	}

	@GetMapping("/finished")
	public ResponseEntity<?> getFinishedVotes(@PathVariable String partyId) {
		log.debug("종료된 투표 목록 조회 시작");
		log.debug("partyId - {}", partyId);
		List<Vote> response = voteService.getVotesByOngoingStatus(partyId, 0);
		return ApiResponse.success(VOTES_READ_SUCCESS, response);
	}

	@GetMapping("/members/{memberId}")
	public ResponseEntity<?> getMyVotes(@PathVariable String partyId, @PathVariable String memberId) {
		log.debug("내가 만든 투표 목록 조회 시작");
		log.debug("partyId - {}, memberId - {}", partyId, memberId);
		List<Vote> response = voteService.getVotesByMemberId(partyId, memberId);
		return ApiResponse.success(VOTES_READ_SUCCESS, response);
	}
}
