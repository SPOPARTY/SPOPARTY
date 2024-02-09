package com.spoparty.api.vote.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.vote.domain.Option;
import com.spoparty.api.vote.domain.Penalty;
import com.spoparty.api.vote.domain.User;
import com.spoparty.api.vote.domain.Vote;
import com.spoparty.api.vote.dto.VoteCreateRequestDTO;
import com.spoparty.api.vote.dto.VoteUpdateRequestDTO;
import com.spoparty.api.vote.repository.OptionRepository;
import com.spoparty.api.vote.repository.PenaltyRepository;
import com.spoparty.api.vote.repository.VoteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {
	private final VoteRepository voteRepository;
	private final OptionRepository optionRepository;
	private final PenaltyRepository penaltyRepository;

	public Vote create(String partyId, VoteCreateRequestDTO dto) {
		// user
		User user = new User(dto.getMemberId(), dto.getNickname());

		// option 등록
		List<Option> options = new ArrayList<>();
		for (String content : dto.getOptions()) {
			Option option = new Option(content);
			optionRepository.save(option);
			options.add(option);
		}

		// penalty 등록
		Penalty penalty = new Penalty(dto.getPenalty());
		penaltyRepository.save(penalty);

		// Vote 등록
		Vote vote = new Vote(user, dto.getTitle(), options, penalty, partyId);
		voteRepository.save(vote);
		log.debug("Vote - {}", vote);
		return getVote(vote.getVoteId());
	}

	public Vote update(String voteId, VoteUpdateRequestDTO voteRequestDTO) {
		Vote vote = getVote(voteId);
		if (voteRequestDTO.getOptionId() != null) { // 투표하기
			log.debug("투표 하기!!");
			increaseOptionCount(vote, voteRequestDTO);
		}
		if (voteRequestDTO.getAnswerOptionId() != null) { // 투표 종료하기
			log.debug("투표 종료하기!!");
			vote.finish(voteRequestDTO.getAnswerOptionId());
		}
		log.debug("수정 후 - {}", vote);
		voteRepository.save(vote);
		return getVote(voteId); // check
	}

	private void increaseOptionCount(Vote vote, VoteUpdateRequestDTO dto) {
		User user = new User(dto.getMemberId(), dto.getNickname());
		for (Option option : vote.getOptions()) {
			if (option.getOptionId().equals(dto.getOptionId())) {
				option.increaseCount();
				option.getUsers().add(user);
				log.debug("Option - {}", option);
				return;
			}
		}
		throw new CustomException(OPTION_NOT_FOUND);
	}

	public Vote getVote(String voteId) {
		return voteRepository.findById(voteId).orElseThrow(() -> new CustomException(VOTE_NOT_FOUND));
	}

	public List<Vote> getVotesByOngoingStatus(String partyId, int ongoing) {
		return voteRepository.findAllByPartyIdAndOngoingOrderByCreatedTimeDesc(partyId, ongoing);
	}

	public List<Vote> getVotesByMemberId(String partyId, String memberId) {
		return voteRepository.findAllByPartyIdAndUser_userIdOrderByCreatedTimeDesc(partyId, memberId);
	}
}
