package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.entity.FixtureEvent;
import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FixtureEventDTO {
	private FixtureEventTeamDTO team;
	private FixtureEventPlayerDTO player;
	private FixtureEventPlayerDTO assist;
	private long time;
	private String type;
	private String detail;

	public static FixtureEventDTO toDTO(FixtureEvent entity) {

		FixtureEventTeamDTO teamDTO = FixtureEventTeamDTO.toDTO(entity.getSeasonLeagueTeam().getTeam());
		FixtureEventPlayerDTO playerDTO = FixtureEventPlayerDTO.toDTO(entity.getPlayer().getPlayer());

		FixtureEventPlayerDTO assistDTO = null;

		if (entity.getAssist() != null) {
			assistDTO = FixtureEventPlayerDTO.toDTO(entity.getAssist().getPlayer());
		}


		return FixtureEventDTO.builder()
			.team(teamDTO)
			.player(playerDTO)
			.assist(assistDTO)
			.time(entity.getTime())
			.type(entity.getType())
			.detail(entity.getDetail())
			.build();
	}
}
