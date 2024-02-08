package com.spoparty.api.football.response;

import org.springframework.data.jpa.repository.Query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StandingSeasonLeagueDTO {
	private String season;
	private Long season_id;
	private Long league_id;
	private Long season_league_id;

	@QueryProjection

	public StandingSeasonLeagueDTO(String season, Long season_id, Long league_id, Long season_league_id) {
		this.season = season;
		this.season_id = season_id;
		this.league_id = league_id;
		this.season_league_id = season_league_id;
	}
}
