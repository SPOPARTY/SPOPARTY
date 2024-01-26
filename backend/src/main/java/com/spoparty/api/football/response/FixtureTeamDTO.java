package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class FixtureTeamDTO {

	private long teamId;

	private String nameKr;

	private String logo;

	public static FixtureTeamDTO toDTO(SeasonLeagueTeam entity){

		return FixtureTeamDTO.builder()
				.teamId(entity.getId())
				.nameKr(entity.getTeam().getNameKr())
				.logo(entity.getTeam().getLogo())
				.build();

	}
}
