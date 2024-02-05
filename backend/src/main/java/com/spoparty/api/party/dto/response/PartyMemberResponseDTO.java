package com.spoparty.api.party.dto.response;

import com.spoparty.api.club.entity.ClubMember;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.party.entity.PartyMemberProjection;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class PartyMemberResponseDTO {
	private Long participantId;
	private Long memberId;
	private String memberNickname;
	private String role;
	private String openviduToken;

	public static PartyMemberResponseDTO toDTO(PartyMemberProjection projection) {
		PartyMemberResponseDTO dto = new PartyMemberResponseDTO();
		dto.participantId = projection.getId();
		dto.memberId = projection.getMember_Id();
		dto.memberNickname = projection.getMember_nickname();
		dto.role = projection.getRole();
		dto.openviduToken = projection.getOpenviduToken();
		return dto;
	}
}
