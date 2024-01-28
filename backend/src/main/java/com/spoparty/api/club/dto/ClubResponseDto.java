package com.spoparty.api.club.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.spoparty.api.club.entity.Club;

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
public class ClubResponseDto {
	private Long clubId;
	private String name;
	private Integer maxParticipants;
	private Integer currentParticipants;
	private Long partyId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedTime;
	private Long hostId;

	public static ClubResponseDto entityToDto(Club entity) {
		ClubResponseDtoBuilder dto = ClubResponseDto.builder()
			.clubId(entity.getId())
			.name(entity.getName())
			.maxParticipants(entity.getMaxParticipants())
			.currentParticipants(entity.getCurrentParticipants())
			.createdTime(entity.getCreatedTime())
			.updatedTime(entity.getUpdatedTime())
			.hostId(entity.getHostMember().getMemberId());

		if (entity.getParty() != null) {
			dto.partyId(entity.getParty().getId());
		}
		return dto.build();
	}
}
