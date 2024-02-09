package com.spoparty.api.party.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.service.ClubServiceImpl;
import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.response.PartyFixtureDTO;
import com.spoparty.api.football.service.FixtureServiceImpl;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.party.dto.request.PartyUpdateRequestDto;
import com.spoparty.api.party.dto.response.PartyResponseDTO;
import com.spoparty.api.party.entity.Party;
import com.spoparty.api.party.entity.PartyMember;
import com.spoparty.api.party.entity.PartyMemberProjection;
import com.spoparty.api.party.repository.PartyMemberRepository;
import com.spoparty.api.party.repository.PartyRepository;
import com.spoparty.security.model.PrincipalDetails;

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
	private final OpenViduService openViduService;
	private final FixtureServiceImpl fixtureService;

	@Override
	@Transactional
	public PartyResponseDTO createParty(PrincipalDetails principalDetails, Long clubId) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		Member member = validatePrincipalDetails(principalDetails); // 로그인 토큰 검증
		Club club = clubService.findClubById(clubId);
		validateNewParty(club); // 이미 파티가 존재하는 경우 체크

		// openvidu session 발급
		String openviduSessionId = getOpenviduSessionId(club);
		log.debug("openviduSessionId - {}", openviduSessionId);

		// party 엔티티 생성
		Party party = Party.createParty(member, club, openviduSessionId);
		partyRepository.save(party);

		// openvidu 커넥션 토큰 발급
		String openviduToken = openViduService.createConnection(openviduSessionId, new HashMap<>());
		log.debug("openviduToken - {}", openviduToken);

		// partyMember 엔티티 생성
		PartyMember partyMember = PartyMember.createPartyMember(party, member, openviduToken, RoleType.host);
		log.debug("partyMember - {}", partyMember);
		partyMemberRepository.save(partyMember);
		return findParty(party.getId());
	}

	private String getOpenviduSessionId(Club club) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		Map<String, Object> params = new HashMap<>();
		params.put("customSessionId", String.valueOf(club.getId())); // customSessionId 세션명 clubId로 지정
		return openViduService.initializeSession(params);
	}

	@Override
	public PartyResponseDTO findParty(Long partyId) {
		Party party = findPartyById(partyId);
		PartyFixtureDTO partyFixtureDTO = null;
		if (party.getFixture() != null) {
			partyFixtureDTO = fixtureRepository.findPartyFixture(party.getFixture().getId());
		}
		return new PartyResponseDTO(party, partyFixtureDTO, countPartyMembers(partyId));
	}

	public Party findPartyById(Long partyId) {
		return partyRepository.findById(partyId).orElseThrow(() -> new CustomException(PARTY_NOT_FOUND));
	}

	@Override
	@Transactional
	public PartyResponseDTO updateParty(Long partyId, PartyUpdateRequestDto updateDto) {
		Party party = findPartyById(partyId);
		log.debug("party - {}", party);

		if (updateDto.getTitle() != null) {
			log.debug("title - {}", updateDto.getTitle());
			party.setTitle(updateDto.getTitle());
		}

		if (updateDto.getFixtureUrl() != null) {
			log.debug("fixtureUrl - {}", updateDto.getFixtureUrl());
			party.setFixtureUrl(updateDto.getFixtureUrl());
		}

		if (updateDto.getFixtureId() != null) {
			Fixture fixture = fixtureService.findFixtureById(updateDto.getFixtureId());
			log.debug("fixture - {}", fixture);
			party.setFixture(fixture);
		}
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

		List<PartyMember> partyMembers = partyMemberRepository.findAllByParty_Id(party.getId(),
			PartyMember.class); // 그룹원 삭제
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
	public PartyMemberProjection createPartyMember(PrincipalDetails principalDetails, Long partyId) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		Member member = validatePrincipalDetails(principalDetails);
		Party party = findPartyById(partyId);

		// 유효성 체크
		validateNewPartyMember(party, member); // 이미 존재하는 멤버인지 확인
		validateParticipants(party); // 남은 자리가 있는지 확인

		// openvidu 커넥션 토큰 발급
		String openviduToken = openViduService.createConnection(party.getOpenviduSessionId(), new HashMap<>());
		log.debug("openviduToken - {}", openviduToken);

		PartyMember partyMember = PartyMember.createPartyMember(party, member, openviduToken, RoleType.guest);
		log.debug("partyMember - {}", partyMember);
		partyMemberRepository.save(partyMember);
		return findPartyMember(partyMember.getId(), PartyMemberProjection.class);
	}

	@Override
	public <T> T findPartyMember(Long partyMemberId, Class<T> type) {
		return partyMemberRepository.findById(partyMemberId, type)
			.orElseThrow(() -> new CustomException(PARTY_MEMBER_NOT_FOUND));
	}

	@Override
	@Transactional
	public Long deletePartyMember(Long partyMemberId) {
		PartyMember partyMember = findPartyMember(partyMemberId, PartyMember.class);
		partyMember.softDelete();
		return partyMember.getId();
	}

	private Member validatePrincipalDetails(PrincipalDetails principalDetails) { // 로그인 토큰 검증
		if (principalDetails == null) {
			throw new CustomException(UNAUTHORIZED_USER);
		}
		return principalDetails.getMember();
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
		Optional<PartyMemberProjection> partyMember = partyMemberRepository.findByParty_IdAndMember_Id(party.getId(),
			member.getId(), PartyMemberProjection.class);
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
