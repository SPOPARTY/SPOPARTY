package com.spoparty.api.club.dto.response;

import com.spoparty.api.club.entity.ClubMember;
import com.spoparty.api.member.entity.Member;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@AllArgsConstructor
@ToString
@Slf4j
public class ClubMemberResponseDTO {
	private Long clubMemberId;
	private Long clubId;
	private Long memberId;
	private String memberNickName;
	private String role;

	public static ClubMemberResponseDTO toDTO(ClubMember entity) {
		ClubMemberResponseDTOBuilder builder = ClubMemberResponseDTO.builder()
			.clubMemberId(entity.getId())
			.role(entity.getRole().name());

		try {
			builder.clubId(entity.getClub().getId());
		} catch (EntityNotFoundException e) {
			log.debug("EntityNotFoundException - Club entity");
			builder.clubId(null);
		}

		try {
			Member member = entity.getMember();
			builder.memberId(member.getId());
			builder.memberNickName(member.getNickname());
		} catch (EntityNotFoundException e) {
			log.debug("EntityNotFoundException - Member entity");
			builder.memberId(null);
			builder.memberNickName(null);
		}

		return builder.build();
	}
}
