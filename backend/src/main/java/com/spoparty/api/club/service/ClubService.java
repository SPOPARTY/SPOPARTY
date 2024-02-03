package com.spoparty.api.club.service;

import java.util.List;

import com.spoparty.api.club.dto.ClubHostRequestDto;
import com.spoparty.api.club.dto.ClubMemberResponseDto;
import com.spoparty.api.club.dto.ClubRequestDto;
import com.spoparty.api.club.dto.ClubResponseDto;
import com.spoparty.api.club.dto.InviteRequestDto;
import com.spoparty.api.club.dto.InviteResponseDto;
import com.spoparty.api.member.entity.Member;
import com.spoparty.security.model.PrincipalDetails;

public interface ClubService {
	List<ClubResponseDto> findRecentClubs(PrincipalDetails principalDetails);

	ClubResponseDto createClub(ClubRequestDto clubRequestDto);

	ClubResponseDto findClub(Long clubId);

	ClubResponseDto updateClubName(PrincipalDetails principalDetails, Long clubId, ClubRequestDto clubRequestDto);

	Long deleteClub(PrincipalDetails principalDetails, Long clubId);

	InviteResponseDto getInviteUrl(Long clubId);

	ClubMemberResponseDto createGroupMember(InviteRequestDto inviteRequestDto);

	List<ClubMemberResponseDto> getGroupMembers(Long clubId);

	ClubMemberResponseDto assignHost(PrincipalDetails principalDetails, Long clubId, ClubHostRequestDto clubHostRequestDto);

	Long deleteGroupMember(PrincipalDetails principalDetails, Long clubId);
}
