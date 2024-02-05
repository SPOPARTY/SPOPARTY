package com.spoparty.api.party.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.entity.ClubMember;
import com.spoparty.api.club.service.ClubServiceImpl;
import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.response.PartyFixtureDTO;
import com.spoparty.api.football.service.FixtureServiceImpl;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.service.MemberService;
import com.spoparty.api.party.dto.request.PartyCreateRequestDto;
import com.spoparty.api.party.dto.request.PartyMemberRequestDto;
import com.spoparty.api.party.dto.response.PartyResponseDTO;
import com.spoparty.api.party.dto.request.PartyUpdateRequestDto;
import com.spoparty.api.party.entity.Party;
import com.spoparty.api.party.entity.PartyMember;
import com.spoparty.api.party.repository.PartyMemberRepository;
import com.spoparty.api.party.repository.PartyRepository;
import com.spoparty.api.party.entity.PartyMemberProjection;

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
	public PartyResponseDTO createParty(PartyCreateRequestDto createDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		Club club = clubService.findClubById(createDto.getClubId());
		validateNewParty(club); // 이미 파티가 존재하는 경우 체크

		Member member = memberService.findById(createDto.getMemberId());

		Map<String, Object> openviduInfo = createDto.getOpenViduSessionInfo();
		log.debug("before : openviduInfo - {}", openviduInfo);
		openviduInfo.put("customSessionId", club.getId().toString()); // customSessionId 세션명을 clubId로 대체
		log.debug("after : openviduInfo - {}", openviduInfo);

		String openviduSessionId = openViduService.initializeSession(
			createDto.getOpenViduSessionInfo()); // openvidu 세션 생성
		log.debug("openviduSessionId - {}", openviduSessionId);

		Party party = Party.createParty(member, club, openviduSessionId);
		partyRepository.save(party);
		return findParty(party.getId());
	}

	@Override
	public PartyResponseDTO findParty(Long partyId) {
		Party party = findPartyById(partyId);
		PartyFixtureDTO partyFixtureDTO = null;
		if (party.getFixture() != null) {
			partyFixtureDTO = fixtureRepository.findPartyFixture(party.getFixture().getId());
		}
		int currentParticipants = countPartyMembers(partyId);
		return new PartyResponseDTO(party, partyFixtureDTO, currentParticipants);
	}

	public Party findPartyById(Long partyId) {
		return partyRepository.findById(partyId).orElseThrow(() -> new CustomException(PARTY_NOT_FOUND));
	}

	@Override
	@Transactional
	public PartyResponseDTO updateParty(Long partyId, PartyUpdateRequestDto updateDto) {
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
		log.debug("party - {}", party);

		Club club = clubService.findClubById(clubId);
		club.setParty(null);
		party.softDelete();

		List<PartyMember> partyMembers = partyMemberRepository.findAllByParty_Id(party.getId(), PartyMember.class); // 그룹원 삭제
		log.debug("삭제할 그룹원 - {}", partyMembers);
		partyMembers.forEach(PartyMember::softDelete);
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

		// 유효성 체크
		validateNewPartyMember(party, member); // 이미 존재하는 멤버인지 확인
		validateParticipants(party); // 남은 자리가 있는지 확인

		// openvidu 커넥션 토큰 발급
		log.debug("openviduSessionId - {}", party.getOpenviduSessionId());
		String openviduToken = openViduService.createConnection(party.getOpenviduSessionId(), requestDto.getOpenViduConnectionInfo());
		log.debug("openviduToken - {}", openviduToken);

		RoleType role = RoleType.guest;
		if (party.getHostMember().equals(member)) { // 호스트인 경우
			role = RoleType.host;
		}

		PartyMember partyMember = PartyMember.createPartyMember(party, member, openviduToken, role);
		log.debug("partyMember - {}", partyMember);
		partyMemberRepository.save(partyMember);
		return findPartyMember(partyMember.getId(), PartyMemberProjection.class);
	}

	@Override
	public <T> T findPartyMember(Long partyMemberId, Class<T> type) {
		return partyMemberRepository.findById(partyMemberId, type).orElseThrow(() -> new CustomException(PARTY_MEMBER_NOT_FOUND));
	}

	@Override
	@Transactional
	public Long deletePartyMember(Long partyMemberId) {
		PartyMember partyMember = findPartyMember(partyMemberId, PartyMember.class);
		partyMember.softDelete();
		return partyMember.getId();
	}

	public int countPartyMembers(Long partyId) {
		return partyMemberRepository.findAllByParty_Id(partyId, PartyMemberProjection.class).size();
	}

	private void validateNewParty(Club club) {
		if (club.getParty() != null) {
			throw new CustomException(ALREADY_PARTY);
		}
	}

	private void validateNewPartyMember(Party party, Member member) {
		Optional<PartyMemberProjection> partyMember = partyMemberRepository.findByParty_IdAndMember_Id(party.getId(), member.getId(), PartyMemberProjection.class);
		if (partyMember.isPresent()) {
			throw new CustomException(ALREADY_PARTY_MEMBER);
		}
	}

	private void validateParticipants(Party party) {
		log.debug("MaxParticipants - {}", party.getMaxParticipants());
		log.debug("currentParticipants - {}", countPartyMembers(party.getId()));
		if (party.getMaxParticipants() <= countPartyMembers(party.getId())) {
			throw new CustomException(ENOUGH_PARTY_PARTICIPANTS);
		}
	}
}
