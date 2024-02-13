package com.spoparty.api.football.response;

import com.querydsl.core.annotations.QueryProjection;
import com.spoparty.api.football.entity.LineupPlayer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FixtureEventPlayerDTO {
	private String nameKr;

	@QueryProjection
	public FixtureEventPlayerDTO(String nameKr) {
		this.nameKr = nameKr;

	}
	// public static FixtureEventPlayerDTO toDTO(LineupPlayer entity) {
	// 	return FixtureEventPlayerDTO.builder()
	// 		.nameKr(entity.getName())
	// 		.build();
	// }
}
