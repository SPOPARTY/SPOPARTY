package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import com.spoparty.api.football.response.SeasonLeagueTeamAllInfoDTO;
import com.spoparty.api.football.response.SeasonLeagueTeamStandingDTO;

public interface StandingsRepositoryCustom {
	public List<SeasonLeagueTeamStandingDTO> findTeamRank(int leagueId);

	public List<SeasonLeagueTeamAllInfoDTO> findTeamAllInfo(int teamId);
}
