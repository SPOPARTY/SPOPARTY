package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.entity.Team;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FixtureEventTeamDTO {
	private String nameKr;
	private String nameEng;
	private String logo;

	public static FixtureEventTeamDTO toDTO(Team entity){
		return FixtureEventTeamDTO.builder()
			.nameKr(entity.getNameKr())
			.nameEng(entity.getNameEng())
			.logo(entity.getLogo())
			.build();
	}
}
