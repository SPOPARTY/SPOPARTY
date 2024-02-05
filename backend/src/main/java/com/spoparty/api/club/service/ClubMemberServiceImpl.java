package com.spoparty.api.club.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.club.dto.request.InviteRequestDTO;
import com.spoparty.api.club.dto.response.ClubMemberResponseDTO;
import com.spoparty.api.club.dto.response.InviteResponseDTO;
import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.entity.ClubMember;
import com.spoparty.api.club.repository.ClubMemberRepository;
import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.member.entity.Member;
import com.spoparty.common.util.CustomEncryptor;
import com.spoparty.security.model.PrincipalDetails;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ClubMemberServiceImpl implements ClubMemberService {
	private final ClubServiceImpl clubService;
	private final ClubMemberRepository clubMemberRepository;
	private final CustomEncryptor encryptor;

	@Value("${club.invite.url.domain}")
	private String INVITE_DOMAIN;
	@Value("${club.invite.url.delimiter}")
	private String INVITE_URL_DELIMITER;
	@Value("${club.invite.expirationDay}")
	private int EXPIRATION_DAY;

	public InviteResponseDTO getInviteUrl(Long clubId) {
		Club club = clubService.findClubById(clubId);
		LocalDateTime createdTime = LocalDateTime.now();
		LocalDateTime expirationTime = createdTime.plusDays(EXPIRATION_DAY);
		log.debug("invite info - club: {}, createdTime: {}, expirationTime: {}", club, createdTime, expirationTime);

		String inviteInfo = club.getId() + INVITE_URL_DELIMITER + expirationTime;
		String inviteUrl = INVITE_DOMAIN + encryptor.encrypt(inviteInfo);
		log.debug("encoded inviteUrl - {}", inviteUrl);
		return new InviteResponseDTO(inviteUrl, createdTime, expirationTime);
	}

	@Transactional
	public ClubMemberResponseDTO createClubMember(PrincipalDetails principalDetails,
		InviteRequestDTO inviteRequestDto) {
		validatePrincipalDetails(principalDetails);
		Member member = principalDetails.getMember();

		// 유효성 체크
		Club club = validateInviteUrl(inviteRequestDto.getInviteUrl()); // 유효한 초대 url 확인
		validateNewClubMember(club, member); // 이미 존재하는 멤버인지 확인

		ClubMember clubMember = ClubMember.createClubMember(member, club, RoleType.guest);
		clubMemberRepository.save(clubMember);
		return getClubMember(clubMember.getId());
	}

	public List<ClubMemberResponseDTO> findClubMembers(Long clubId) {
		List<ClubMember> clubMembers = clubMemberRepository.findAllByClub_Id(clubId);
		List<ClubMemberResponseDTO> result = new ArrayList<>();
		for (ClubMember clubMember : clubMembers) {
			try {
				Member member = clubMember.getMember();
				if (member != null && member.getState() != 2) { // 탈퇴가 아닌 경우만 추가
					result.add(ClubMemberResponseDTO.toDTO(clubMember));
				}
			} catch (EntityNotFoundException e) {
				log.debug("clubMember.getMember() EntityNotFoundException 발생!!!");
			}
		}
		return result;
		// return clubMembers.stream()
		// 	.filter(
		// 		clubMember -> clubMember.getMember() != null && clubMember.getMember().getState() != 2) // 탈퇴가 아닌 경우만 체크
		// 	.map(ClubMemberResponseDTO::toDTO)
		// 	.collect(Collectors.toList());
	}

	public ClubMemberResponseDTO getClubMember(Long clubMemberId) {
		ClubMember clubMember = getClubMemberById(clubMemberId);
		return ClubMemberResponseDTO.toDTO(clubMember);
	}

	@Transactional
	public ClubMemberResponseDTO assignHost(PrincipalDetails principalDetails, Long clubId, Long clubMemberId) {
		validatePrincipalDetails(principalDetails);
		Member hostMember = principalDetails.getMember();

		ClubMember currentHost = getClubMember(clubId, hostMember.getId());
		ClubMember nextHost = getClubMemberById(clubMemberId);

		validateHost(currentHost); //호스트 권한 체크

		if (!currentHost.equals(nextHost)) {
			currentHost.getClub().setHostMember(nextHost.getMember());
			currentHost.setRole(RoleType.guest);
			nextHost.setRole(RoleType.host);
		}
		return ClubMemberResponseDTO.toDTO(nextHost);
	}

	@Transactional
	public Long deleteClubMember(PrincipalDetails principalDetails, Long clubId) {
		validatePrincipalDetails(principalDetails);
		Member member = principalDetails.getMember();
		log.debug("member - {} ", member);

		Club club = clubService.findClubById(clubId);
		log.debug("club - {} ", club);

		ClubMember clubMember = getClubMember(club.getId(), member.getId());
		log.debug("clubMember - {} ", clubMember);

		if (countClubMembers(clubId) == 1) { // 남아있는 그룹원이 없는 경우 -> 그룹도 삭제됨
			club.softDelete();
		} else if (clubMember.getRole().equals(RoleType.host)) { // 그룹장이 나가는 경우 -> 그룹원 넘겨야 함
			throw new CustomException(HOST_CANNOT_LEAVE_GROUP);
		}

		clubMember.softDelete(); // 그룹원 삭제
		return clubMember.getId();
	}

	@Transactional
	public Long deleteClubMemberByHost(PrincipalDetails principalDetails, Long clubId, Long clubMemberId) {
		validatePrincipalDetails(principalDetails);
		Member hostMember = principalDetails.getMember();

		ClubMember host = getClubMember(clubId, hostMember.getId());

		validateHost(host); //호스트 권한 체크

		ClubMember guest = getClubMemberById(clubMemberId); // 그룹원 확인
		if (!hostMember.equals(guest.getMember())) {
			guest.softDelete();
		}
		return guest.getId();
	}

	public ClubMember getClubMemberById(Long clubMemberId) {
		return clubMemberRepository.findById(clubMemberId)
			.orElseThrow(() -> new CustomException(CLUB_MEMBER_NOT_FOUND));
	}

	private ClubMember getClubMember(Long clubId, Long memberId) {
		return clubMemberRepository.findByClub_IdAndMember_Id(clubId, memberId)
			.orElseThrow(() -> new CustomException(CLUB_MEMBER_NOT_FOUND));
	}

	public int countClubMembers(Long clubId) {
		return clubMemberRepository.findAllByClub_Id(clubId).size();
	}

	private void validatePrincipalDetails(PrincipalDetails principalDetails) { // 로그인 토큰 검증
		if (principalDetails == null) {
			throw new CustomException(UNAUTHORIZED_USER);
		}
	}

	private void validateHost(ClubMember clubMember) {
		if (!clubMember.getRole().equals(RoleType.host)) {
			log.debug("그룹장 권한 없음!!");
			log.debug("요청한 clubMember - {}", clubMember);
			throw new CustomException(CLUB_NOT_FOUND); // 403 대신 404 처리
		}
	}

	private Club validateInviteUrl(String inviteUrl) {
		try {
			String encodedCode = inviteUrl.replace(INVITE_DOMAIN, "");
			log.debug("encodedCode - {}", encodedCode);
			String decodedCode = encryptor.decrypt(encodedCode);
			log.debug("decodedCode - {}", decodedCode);

			StringTokenizer st = new StringTokenizer(decodedCode, INVITE_URL_DELIMITER);
			Long clubId = Long.parseLong(st.nextToken());
			LocalDateTime expirationTime = LocalDateTime.parse(st.nextToken());
			log.debug("cludId - {}", clubId);
			log.debug("currentTime - {}", LocalDateTime.now());
			log.debug("expirationTime - {}", expirationTime);

			if (LocalDateTime.now().isBefore(expirationTime)) { // 만료시간 이내인 경우만 유효함
				return clubService.findClubById(clubId);
			}
		} catch (Exception e) {
			log.debug("연산 중 오류");
		}
		throw new CustomException(INVALID_INVITE_URL);
	}

	private void validateNewClubMember(Club club, Member member) {
		Optional<ClubMember> clubMember = clubMemberRepository.findByClub_IdAndMember_Id(club.getId(), member.getId());
		if (clubMember.isPresent()) {
			throw new CustomException(ALREADY_GROUP_MEMBER);
		}
	}

}
