package com.spoparty.api.club.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.club.dto.request.ClubRequestDTO;
import com.spoparty.api.club.dto.response.ClubResponseDTO;
import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.entity.ClubMember;
import com.spoparty.api.club.repository.ClubMemberRepository;
import com.spoparty.api.club.repository.ClubRepository;
import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.member.entity.Member;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ClubServiceImpl implements ClubService {
	private final ClubRepository clubRepository;
	private final ClubMemberRepository clubMemberRepository;

	public List<ClubResponseDTO> findRecentClubs(PrincipalDetails principalDetails) {
		Member member = validatePrincipalDetails(principalDetails);
		// return clubRepository.findClubsAndClubMemberCountOrderByTime(member.getId());
		List<ClubMember> clubMembers = clubMemberRepository.findAllByMember(member);

		return clubMembers.stream()
			.map(clubMember -> new ClubResponseDTO(
				clubMember.getClub(),
				clubMemberRepository.findAllByClub(clubMember.getClub()).size())
			)
			.sorted(Comparator.comparing(ClubResponseDTO::getUpdatedTime).reversed())
			.collect(Collectors.toList());
	}

	@Transactional
	public ClubResponseDTO createClub(PrincipalDetails principalDetails, ClubRequestDTO clubRequestDTO) {
		validatePrincipalDetails(principalDetails);
		Member member = principalDetails.getMember();

		Club club = Club.createClub(clubRequestDTO.getName(), member);
		clubRepository.save(club);

		ClubMember clubMember = ClubMember.createClubMember(member, club, RoleType.host); // 클럽회원 생성 - 호스트
		clubMemberRepository.save(clubMember);
		return findClub(club.getId());
	}

	public ClubResponseDTO findClub(Long clubId) {
		Club club = findClubById(clubId);
		int currentParticipants = clubMemberRepository.findAllByClub(club).size();
		return new ClubResponseDTO(club, currentParticipants);
	}

	@Transactional
	public ClubResponseDTO updateClubName(PrincipalDetails principalDetails, Long clubId, ClubRequestDTO clubRequestDTO) { // 그룹장 권한
		validatePrincipalDetails(principalDetails);
		Member member = principalDetails.getMember();
		Club club = findClubById(clubId);

		validateHost(club, member); //호스트 권한 체크

		club.setName(clubRequestDTO.getName());
		return findClub(club.getId());
	}

	@Transactional
	public Long deleteClub(PrincipalDetails principalDetails, Long clubId) { // 그룹장 권한
		validatePrincipalDetails(principalDetails);
		Member member = principalDetails.getMember();
		Club club = findClubById(clubId);

		validateHost(club, member); //호스트 권한 체크
		club.softDelete();

		List<ClubMember> clubMembers = clubMemberRepository.findAllByClub_Id(clubId); // 그룹원 삭제
		log.debug("삭제할 그룹원 - {}", clubMembers);
		clubMembers.forEach(ClubMember::softDelete);

		return club.getId();
	}

	public Club findClubById(Long clubId) {
		return clubRepository.findById(clubId).orElseThrow(() -> new CustomException(CLUB_NOT_FOUND));
	}

	private Member validatePrincipalDetails(PrincipalDetails principalDetails) { // 로그인 토큰 검증
		if (principalDetails == null) {
			throw new CustomException(UNAUTHORIZED_USER);
		}
		return principalDetails.getMember();
	}

	private void validateHost(Club club, Member member) {
		if (!member.getId().equals(club.getHostMember().getId())) {
			log.debug("그룹장 권한 없음!!");
			log.debug("요청한 member - {}", member);
			log.debug("그룹장 member - {}", club.getHostMember());
			throw new CustomException(CLUB_NOT_FOUND); // 403 대신 404 처리
		}
	}
}
