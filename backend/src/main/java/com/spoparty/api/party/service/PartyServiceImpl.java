package com.spoparty.api.party.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.service.ClubServiceImpl;
import com.spoparty.api.common.exception.NotFoundException;
import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.response.PartyFixtureDTO;
import com.spoparty.api.football.service.FixtureServiceImpl;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.service.MemberService;
import com.spoparty.api.party.dto.PartyCreateRequestDto;
import com.spoparty.api.party.dto.PartyMemberRequestDto;
import com.spoparty.api.party.dto.PartyResponseDto;
import com.spoparty.api.party.dto.PartyUpdateRequestDto;
import com.spoparty.api.party.entity.Party;
import com.spoparty.api.party.entity.PartyMember;
import com.spoparty.api.party.repository.PartyMemberRepository;
import com.spoparty.api.party.repository.PartyRepository;
import com.spoparty.api.party.repository.projection.PartyMemberProjection;

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
	private final FixtureRepository fixtureRepository;
	private final ClubServiceImpl clubService;
	private final MemberService memberService;
	private final OpenViduService openViduService;
	private final FixtureServiceImpl fixtureService;

	@Override
	@Transactional
	public PartyResponseDto createParty(PartyCreateRequestDto createDto) throws OpenViduJavaClientException, OpenViduHttpException {
		Club club = clubService.findClubById(createDto.getClubId());
		Member member = memberService.findById(createDto.getMemberId());
		Map<String, Object> openviduInfo = createDto.getOpenViduSessionInfo();
		log.debug("before : openviduInfo - {}", openviduInfo);
		openviduInfo.put("customSessionId", club.getId().toString()); // customSessionId 세션명을 clubId로 대체
		// openviduInfo.put("customSessionId", UUID.randomUUID().toString()); // customSessionId 세션명을 uuid로 대체
		log.debug("after : openviduInfo - {}", openviduInfo);
		String openviduSessionId = openViduService.initializeSession(createDto.getOpenViduSessionInfo()); // openvidu 세션 생성
		log.debug("openviduSessionId - {}", openviduSessionId);
		Party party = Party.createParty(member, club, openviduSessionId);
		partyRepository.save(party);
		return findParty(party.getId());
	}

	@Override
	public PartyResponseDto findParty(Long partyId) {
		Party party = findPartyById(partyId);
		PartyFixtureDTO partyFixtureDTO = null;
		if (party.getFixture() != null) {
			partyFixtureDTO = fixtureRepository.findPartyFixture(party.getFixture().getId());
		}
		return PartyResponseDto.toDto(party, partyFixtureDTO);
	}

	public Party findPartyById(Long partyId) {
		return partyRepository.findById(partyId).orElseThrow(() -> new NotFoundException(PARTY_NOT_FOUND));
	}

	@Override
	@Transactional
	public PartyResponseDto updateParty(Long partyId, PartyUpdateRequestDto updateDto) {
		Party party = findPartyById(partyId);
		log.debug("party - {}", party);
		Fixture fixture = fixtureService.findFixtureById(updateDto.getFixtureId());
		log.debug("fixture - {}", fixture);
		party.setTitle(updateDto.getTitle());
		party.setFixtureUrl(updateDto.getFixtureUrl());
		party.setFixture(fixture);
		return findParty(party.getId());
	}

	@Override
	@Transactional
	public Long deleteParty(Long partyId, Long clubId) {
		Party party = findPartyById(partyId);
		Club club = clubService.findClubById(clubId);
		log.debug("party - {}", party);
		party.softDelete();
		club.setParty(null);
		return party.getId();
	}

	@Override
	public List<PartyMemberProjection> findAllPartyMembers(Long partyId) {
		return partyMemberRepository.findAllByParty_Id(partyId, PartyMemberProjection.class);
	}

	@Override
	@Transactional
	public PartyMemberProjection createPartyMember(Long partyId, PartyMemberRequestDto requestDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		Party party = findPartyById(partyId);
		Member member = memberService.findById(requestDto.getMemberId());
		log.debug("openviduSessionId - {}", party.getOpenviduSessionId());
		String openviduToken = openViduService.createConnection(party.getOpenviduSessionId(), requestDto.getOpenViduConnectionInfo());
		log.debug("openviduToken - {}", openviduToken);
		PartyMember partyMember = PartyMember.createPartyMember(party, member, openviduToken);
		log.debug("partyMember - {}", partyMember);
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
		Party party = findPartyById(partyId);

		party.decreaseParticipants();
		partyMember.softDelete();
		return partyMember.getId();
	}
}
