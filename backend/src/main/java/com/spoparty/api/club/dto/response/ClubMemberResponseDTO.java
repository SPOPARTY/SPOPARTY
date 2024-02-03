package com.spoparty.api.club.dto.response;

import com.spoparty.api.club.entity.ClubMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ClubMemberResponseDTO {
	private Long clubMemberId;
	private Long clubId;
	private Long memberId;
	private String memberNickName;
	private String role;

	public static ClubMemberResponseDTO toDTO(ClubMember entity) {
		return ClubMemberResponseDTO.builder()
			.clubMemberId(entity.getId())
			.clubId(entity.getClub().getId())
			.memberId(entity.getMember().getId())
			.memberNickName(entity.getMember().getNickname())
			.role(entity.getRole().name())
			.build();
	}
}
