package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.SeasonLeague;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class KeywordSeasonLeagueDTO {

	private long leagueId;

	private String nameKr;

	public static KeywordSeasonLeagueDTO toDTO(SeasonLeague entity) {
		return KeywordSeasonLeagueDTO.builder()
			.leagueId(entity.getId())
			.nameKr(entity.getLeague().getNameKr())
			.build();

	}
}
