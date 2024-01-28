package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class SeasonLeagueTeamStandingDTO {
	private long teamId;

	private String nameKr;

	private String nameEng;

	private String logo;

	private StandingDTO standing;

	public static SeasonLeagueTeamStandingDTO toDTO(SeasonLeagueTeam entity) {

		StandingDTO standing = StandingDTO.toDTO(entity.getStanding());

		return SeasonLeagueTeamStandingDTO.builder()
			.teamId(entity.getId())
			.nameKr(entity.getTeam().getNameKr())
			.nameEng(entity.getTeam().getNameEng())
			.logo(entity.getTeam().getLogo())
			.standing(standing)
			.build();
	}
}
