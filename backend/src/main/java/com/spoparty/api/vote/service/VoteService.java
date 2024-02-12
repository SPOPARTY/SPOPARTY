package com.spoparty.api.vote.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.vote.domain.Option;
import com.spoparty.api.vote.domain.Penalty;
import com.spoparty.api.vote.domain.User;
import com.spoparty.api.vote.domain.Vote;
import com.spoparty.api.vote.dto.request.VoteCountingRequestDTO;
import com.spoparty.api.vote.dto.request.VoteCreateRequestDTO;
import com.spoparty.api.vote.dto.request.VoteRequestDTO;
import com.spoparty.api.vote.dto.response.VoteCountingResponseDTO;
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

	public Vote create(VoteCreateRequestDTO dto) {
		// user
		User user = new User(dto.getMemberId(), dto.getNickname());

		// option 등록
		List<Option> options = new ArrayList<>();
		for (String content : dto.getOptions()) {
			Option option = new Option(content);
			optionRepository.save(option);
			options.add(option);
		}
		log.debug("options {}", options);

		// penalty 등록
		Penalty penalty = new Penalty(dto.getPenalty());
		penaltyRepository.save(penalty);

		// Vote 등록
		Vote vote = new Vote(user, dto.getTitle(), options, penalty, dto.getPartyId());
		voteRepository.save(vote);
		log.debug("생성 후 Vote - {}", vote);
		return getVote(vote.getVoteId());
	}

	public Vote updateOption(VoteRequestDTO dto) {
		Vote vote = getVote(dto.getVoteId());
		increaseOptionCount(vote, dto);
		log.debug("투표 진행 후 - {}", vote);
		voteRepository.save(vote);
		return getVote(dto.getVoteId()); // check
	}

	public VoteCountingResponseDTO updateAnswer(VoteCountingRequestDTO dto) {
		Vote vote = getVote(dto.getVoteId());
		vote.finish(dto.getAnswerOptionId());
		voteRepository.save(vote);
		return findPenaltyUsers(vote);
	}

	private VoteCountingResponseDTO findPenaltyUsers(Vote vote) {
		Set<User> penaltyUsers = new HashSet<>();
		String answerOptionId = vote.getAnswerOptionId();
		String answerOptionContent = null;
		for (Option option : vote.getOptions()) {
			if (answerOptionId.equals(option.getOptionId())) {
				log.debug("정답 option - {}", option);
				answerOptionContent = option.getContent();
			} else {
				log.debug("add penalty user - {}", option.getUsers());
				penaltyUsers.addAll(option.getUsers());
			}
		}
		log.debug("penaltyUsers - {}", penaltyUsers);
		return new VoteCountingResponseDTO(vote, List.copyOf(penaltyUsers), answerOptionContent);
	}

	private void increaseOptionCount(Vote vote, VoteRequestDTO dto) {
		User user = new User(dto.getMemberId(), dto.getNickname());
		for (Option option : vote.getOptions()) {
			if (option.getOptionId().equals(dto.getOptionId())) {
				option.increaseCount();
				option.getUsers().add(user);
				log.debug("Option - {}", option);
				vote.increaseCount();
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
