package com.spoparty.api.club.dto;

import com.spoparty.api.club.entity.ClubMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClubMemberResponseDto {
	private Long clubMemberId;
	private Long clubId;
	private Long memberId;
	private String role;

	public static ClubMemberResponseDto entityToDto(ClubMember entity) {
		return ClubMemberResponseDto.builder()
			.clubMemberId(entity.getId())
			.clubId(entity.getClub().getId())
			.memberId(entity.getMember().getId())
			.role(entity.getRole().name())
			.build();
	}
}
