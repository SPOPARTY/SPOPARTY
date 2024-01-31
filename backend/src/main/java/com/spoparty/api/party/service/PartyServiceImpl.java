package com.spoparty.api.party.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.service.ClubServiceImpl;
import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.common.exception.NotFoundException;
import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.service.FixtureServiceImpl;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.service.MemberService;
import com.spoparty.api.party.dto.PartyCreateRequestDto;
import com.spoparty.api.party.dto.PartyUpdateRequestDto;
import com.spoparty.api.party.entity.Party;
import com.spoparty.api.party.entity.PartyMember;
import com.spoparty.api.party.repository.PartyMemberRepository;
import com.spoparty.api.party.repository.PartyRepository;
import com.spoparty.api.party.repository.projection.PartyMemberProjection;
import com.spoparty.api.party.repository.projection.PartyProjection;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PartyServiceImpl implements PartyService {
	private final PartyRepository partyRepository;
	private final PartyMemberRepository partyMemberRepository;
	private final ClubServiceImpl clubService;
	private final MemberService memberService;
	private final OpenViduService openViduService;
	private final FixtureServiceImpl fixtureService;

	@Override
	@Transactional
	public PartyProjection createParty(PartyCreateRequestDto createDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		Club club = clubService.findClubById(createDto.getClubId());
		Member member = memberService.findById(createDto.getMemberId());
		Fixture fixture = fixtureService.findFixtureById(createDto.getFixtureId());
		String openviduSessionId = openViduService.initializeSession(createDto.getOpenViduInfo()); // openvidu 세션 생성

		Party party = Party.createParty(createDto.getTitle(), createDto.getFixtureUrl(), fixture, member, club, openviduSessionId);
		partyRepository.save(party);
		createPartyMember(party.getId(), member.getId(), RoleType.host); // 호스트 추가

		return findParty(party.getId(), PartyProjection.class);
	}

	@Override
	public <T> T findParty(Long partyId, Class<T> type) {
		return partyRepository.findById(partyId, type).orElseThrow(() -> new NotFoundException(PARTY_NOT_FOUND));
	}

	@Override
	@Transactional
	public PartyProjection updateParty(Long partyId, PartyUpdateRequestDto updateDto) {
		Party party = findParty(partyId, Party.class);
		Fixture fixture = fixtureService.findFixtureById(updateDto.getFixtureId());

		party.setTitle(updateDto.getTitle());
		party.setFixtureUrl(updateDto.getFixtureUrl());
		party.setFixture(fixture);
		return findParty(party.getId(), PartyProjection.class);
	}

	@Override
	@Transactional
	public Long deleteParty(Long partyId) {
		Party party = findParty(partyId, Party.class);
		party.softDelete();
		return party.getId();
	}

	@Override
	public List<PartyMemberProjection> findAllPartyMembers(Long partyId) {
		return partyMemberRepository.findAllByParty_Id(partyId, PartyMemberProjection.class);
	}

	@Override
	@Transactional
	public PartyMemberProjection createPartyMember(Long partyId, Long memberId, RoleType role) {
		Party party = findParty(partyId, Party.class);
		Member member = memberService.findById(memberId);

		PartyMember partyMember = PartyMember.createPartyMember(party, member, role);
		partyMemberRepository.save(partyMember);
		return findPartyMember(partyMember.getId(), PartyMemberProjection.class);
	}

	@Override
	public <T> T findPartyMember(Long partyMemberId, Class<T> type) {
		return partyMemberRepository.findById(partyMemberId, type).orElseThrow(() -> new NotFoundException(
			PARTY_MEMBER_NOT_FOUND));
	}

	@Override
	@Transactional
	public Long deletePartyMember(Long partyMemberId) {
		PartyMember partyMember = findPartyMember(partyMemberId, PartyMember.class);
		Long partyId = partyMember.getParty().getId();
		Party party = findParty(partyId, Party.class);

		party.decreaseParticipants();
		partyMember.softDelete();
		return partyMember.getId();
	}
}
