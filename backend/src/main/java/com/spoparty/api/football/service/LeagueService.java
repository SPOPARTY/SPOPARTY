package com.spoparty.api.football.service;

import java.util.List;

import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.response.SeasonLeagueDTO;
import com.spoparty.api.football.response.SeasonLeagueTeamStandingDTO;

public interface LeagueService {
	public ResponseDTO findAllLeague();

	public ResponseDTO findTeamRank(int leagueId);
}
