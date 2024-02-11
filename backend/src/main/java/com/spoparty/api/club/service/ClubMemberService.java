package com.spoparty.api.club.service;

import java.util.List;

import com.spoparty.api.club.dto.request.InviteRequestDTO;
import com.spoparty.api.club.dto.response.ClubMemberResponseDTO;
import com.spoparty.api.club.dto.response.InviteResponseDTO;
import com.spoparty.api.member.entity.Member;
import com.spoparty.security.model.PrincipalDetails;

public interface ClubMemberService {
	InviteResponseDTO getInviteUrl(Long clubId);

	ClubMemberResponseDTO createClubMember(PrincipalDetails principalDetails, InviteRequestDTO inviteRequestDto);

	List<ClubMemberResponseDTO> findClubMembers(Long clubId);

	ClubMemberResponseDTO assignHost(PrincipalDetails principalDetails, Long clubId, Long clubMemberId);

	Long deleteClubMember(PrincipalDetails principalDetails, Long clubId);

	Long deleteClubMemberByHost(PrincipalDetails principalDetails, Long clubId, Long clubMemberId);

	int deleteClubMemberFromAllClub(Member member);
}
