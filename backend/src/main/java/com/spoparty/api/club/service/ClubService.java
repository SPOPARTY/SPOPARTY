package com.spoparty.api.club.service;

import java.util.List;

import com.spoparty.api.club.dto.request.ClubRequestDTO;
import com.spoparty.api.club.dto.response.ClubResponseDTO;
import com.spoparty.security.model.PrincipalDetails;

public interface ClubService {
	List<ClubResponseDTO> findRecentClubs(PrincipalDetails principalDetails);

	ClubResponseDTO createClub(PrincipalDetails principalDetails, ClubRequestDTO clubRequestDto);

	ClubResponseDTO findClub(Long clubId);

	ClubResponseDTO updateClubName(PrincipalDetails principalDetails, Long clubId, ClubRequestDTO clubRequestDto);

	Long deleteClub(PrincipalDetails principalDetails, Long clubId);
}
