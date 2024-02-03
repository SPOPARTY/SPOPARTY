package com.spoparty.api.club.dto.response;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.spoparty.api.club.entity.Club;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ClubResponseDTO {
	private Long clubId;
	private String name;
	private Integer maxParticipants;
	private Integer currentParticipants;
	private Long partyId;
	private String hostName;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedTime;

	public static ClubResponseDTO toDTO(Club entity, int currentParticipants) {
		ClubResponseDTOBuilder dto = ClubResponseDTO.builder()
			.clubId(entity.getId())
			.name(entity.getName())
			.maxParticipants(entity.getMaxParticipants())
			.currentParticipants(currentParticipants)
			.hostName(entity.getHostMember().getNickname())
			.createdTime(entity.getCreatedTime())
			.updatedTime(entity.getUpdatedTime());

		if (entity.getParty() != null) {
			dto.partyId(entity.getParty().getId());
		}
		return dto.build();
	}
}
