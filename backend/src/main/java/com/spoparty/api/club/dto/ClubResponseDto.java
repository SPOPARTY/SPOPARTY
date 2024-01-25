package com.spoparty.api.club.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubResponseDto {
	private long clubId;
	private String name;
	private int maxParticipants;
	private int currentParticipants;
	private long partyId;
	private LocalDateTime updatedTime;
}
