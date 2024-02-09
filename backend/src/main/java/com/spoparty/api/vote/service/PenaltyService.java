package com.spoparty.api.vote.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.vote.domain.Penalty;
import com.spoparty.api.vote.dto.PenaltyRequestDTO;
import com.spoparty.api.vote.repository.PenaltyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PenaltyService {
	private final PenaltyRepository penaltyRepository;

	public Penalty create(PenaltyRequestDTO penaltyRequestDTO) {
		Penalty penalty = new Penalty(penaltyRequestDTO.getContent());
		penaltyRepository.save(penalty);
		return penalty;
	}

	public List<Penalty> getAllPenalties() {
		return (List<Penalty>)penaltyRepository.findAll();
	}

	public Penalty getPenalty(String penaltyId) {
		return penaltyRepository.findById(penaltyId)
			.orElseThrow(() -> new CustomException(PENALTY_NOT_FOUND));
	}
}
