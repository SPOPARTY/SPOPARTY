package com.spoparty.api.vote.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.vote.domain.Vote;
import com.spoparty.api.vote.dto.VoteCountingRequestDTO;
import com.spoparty.api.vote.dto.VoteCreateRequestDTO;
import com.spoparty.api.vote.dto.VoteRequestDTO;
import com.spoparty.api.vote.service.VoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VoteSocketController {
	private final VoteService voteService;
	private final SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("vote/create")
	public void create(@Payload @Valid VoteCreateRequestDTO voteRequestDTO) {
		log.debug("투표 생성 시작");
		log.debug("VoteCreateRequestDTO - {}", voteRequestDTO);
		Vote response = voteService.create(voteRequestDTO);
		simpMessagingTemplate.convertAndSend("/sub/vote/" + voteRequestDTO.getPartyId(), response);
	}

	@MessageMapping("vote/do")
	public void doVote(@Payload @Valid VoteRequestDTO voteRequestDTO) {
		log.debug("투표 시작");
		log.debug("VoteRequestDTO - {}", voteRequestDTO);
		Vote response = voteService.updateOption(voteRequestDTO);
		simpMessagingTemplate.convertAndSend("/sub/vote/" + voteRequestDTO.getPartyId(), response);
	}

	@MessageMapping("vote/counting")
	public void countVote(@Payload @Valid VoteCountingRequestDTO voteCountingRequestDTO) {
		log.debug("투표 집계 시작");
		log.debug("VoteCountingRequestDTO - {}", voteCountingRequestDTO);
		Vote response = voteService.updateAnswer(voteCountingRequestDTO);
		simpMessagingTemplate.convertAndSend("/sub/vote/" + voteCountingRequestDTO.getPartyId(), response);
	}
}
