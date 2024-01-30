package com.spoparty.api.party.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.party.dto.PartyMemberRequestDto;
import com.spoparty.api.party.dto.PartyRequestDto;
import com.spoparty.api.party.repository.PartyMemberRepository;
import com.spoparty.api.party.repository.PartyRepository;
import com.spoparty.api.party.repository.projection.PartyMemberProjection;
import com.spoparty.api.party.repository.projection.PartyProjection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PartyServiceImpl implements PartyService {
	private final PartyRepository partyRepository;
	private final PartyMemberRepository partyMemberRepository;

	@Override
	@Transactional
	public PartyProjection createParty(PartyRequestDto partyRequestDto) {
		return null;
	}

	@Override
	public PartyProjection findParty(Long partyId) {
		return null;
	}

	@Override
	@Transactional
	public PartyProjection updateParty(Long partyId, PartyRequestDto partyRequestDto) {
		return null;
	}

	@Override
	@Transactional
	public Long deleteParty(Long partyId) {
		return null;
	}

	@Override
	public List<PartyMemberProjection> getAllPartyMembers(Long partyId) {
		return null;
	}

	@Override
	@Transactional
	public PartyMemberProjection createPartyMember(Long partyId, PartyMemberRequestDto partyMemberRequestDto) {
		return null;
	}

	@Override
	public PartyMemberProjection getPartyMember(Long partyId, Long memberId) {
		return null;
	}

	@Override
	@Transactional
	public Long deletePartyMember(Long partyId, Long memberId) {
		return null;
	}
}
