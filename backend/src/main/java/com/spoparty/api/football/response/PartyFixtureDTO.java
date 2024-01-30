package com.spoparty.api.football.response;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PartyFixtureDTO {
	private String leagueName;
	private String round;
	private LocalDateTime startTime;
	private KeywordSeasonLeagueTeamDTO homeTeam;
	private KeywordSeasonLeagueTeamDTO awayTeam;

	@QueryProjection
	public PartyFixtureDTO(String leagueName, String round, LocalDateTime startTime,
		KeywordSeasonLeagueTeamDTO homeTeam,
		KeywordSeasonLeagueTeamDTO awayTeam) {
		this.leagueName = leagueName;
		this.round = round;
		this.startTime = startTime;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
}
