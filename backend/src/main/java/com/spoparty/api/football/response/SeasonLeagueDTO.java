package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.SeasonLeague;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class SeasonLeagueDTO {

	private long leagueId;

	private String nameKr;

	private String logo;

	public static SeasonLeagueDTO toDTO(SeasonLeague entity) {

		return SeasonLeagueDTO.builder()
			.leagueId(entity.getId())
			.nameKr(entity.getLeague().getNameKr())
			.logo(entity.getLeague().getLogo())
			.build();

	}
}
