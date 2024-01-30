package com.spoparty.api.football.response;

import com.querydsl.core.annotations.QueryProjection;
import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class KeywordSeasonLeagueTeamDTO {

	private long teamId;

	private String name;

	@QueryProjection
	public KeywordSeasonLeagueTeamDTO(long teamId, String nameKr) {
		this.teamId = teamId;
		this.name = nameKr;
	}

	public static KeywordSeasonLeagueTeamDTO toDTO(SeasonLeagueTeam entity) {

		return KeywordSeasonLeagueTeamDTO.builder()
			.teamId(entity.getId())
			.name(entity.getTeam().getNameKr())
			.build();

	}
}
