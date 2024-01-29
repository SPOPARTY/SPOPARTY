package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class KeywordSeasonLeagueTeamDTO {

	private long teamId;

	private String nameKr;

	public static KeywordSeasonLeagueTeamDTO toDTO(SeasonLeagueTeam entity) {

		return KeywordSeasonLeagueTeamDTO.builder()
			.teamId(entity.getId())
			.nameKr(entity.getTeam().getNameKr())
			.build();

	}
}
