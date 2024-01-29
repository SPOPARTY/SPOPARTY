package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class SeasonLeagueTeamDTO {

	private long teamId;

	private String nameKr;

	private String nameEng;

	private String logo;

	public static SeasonLeagueTeamDTO toDTO(SeasonLeagueTeam entity) {

		return SeasonLeagueTeamDTO.builder()
			.teamId(entity.getId())
			.nameKr(entity.getTeam().getNameKr())
			.nameEng(entity.getTeam().getNameEng())
			.logo(entity.getTeam().getLogo())
			.build();

	}
}
