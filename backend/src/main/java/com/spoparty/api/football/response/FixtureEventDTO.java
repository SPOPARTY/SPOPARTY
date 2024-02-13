package com.spoparty.api.football.response;

import com.querydsl.core.annotations.QueryProjection;
import com.spoparty.api.football.entity.FixtureEvent;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FixtureEventDTO {
	private FixtureEventTeamDTO team;
	private String playerName;
	private String assistName;
	private long time;
	private String type;
	private String detail;

	@QueryProjection
	public FixtureEventDTO(
		FixtureEventTeamDTO team,
		String player,
		String assist,
		long time,
		String type, String detail) {
		this.team = team;
		this.playerName = player;
		this.assistName = assist;
		this.time = time;
		this.type = type;
		this.detail = detail;
	}
	// public static FixtureEventDTO toDTO(FixtureEvent entity) {
	//
	// 	FixtureEventTeamDTO teamDTO = FixtureEventTeamDTO.toDTO(entity.getSeasonLeagueTeam().getTeam());
	//
	//
	// 	String assistName = null;
	//
	// 	if (entity.getAssist() != null) {
	// 		assistName = entity.getAssist().getName();
	// 	}
	//
	//
	// 	return FixtureEventDTO.builder()
	// 		.team(teamDTO)
	// 		.playerName(entity.getPlayer().getName())
	// 		.assistName(assistName)
	// 		.time(entity.getTime())
	// 		.type(entity.getType())
	// 		.detail(entity.getDetail())
	// 		.build();
	// }
}
