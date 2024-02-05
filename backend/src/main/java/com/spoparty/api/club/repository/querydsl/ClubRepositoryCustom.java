package com.spoparty.api.club.repository.querydsl;

import java.util.List;

import com.spoparty.api.club.dto.response.ClubResponseDTO;

public interface ClubRepositoryCustom {
	List<ClubResponseDTO> findClubsAndClubMemberCountOrderByTime(Long memberId);
}
