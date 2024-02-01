package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.entity.Team;
import com.spoparty.api.football.response.EmblemDTO;

public interface SeasonLeagueTeamRepositoryCustom {

	public List<SeasonLeagueTeam> findTeamByKeyword(String keyword);

	public List<SeasonLeagueTeam> findTeamRank(int leagueId);

	public List<SeasonLeagueTeam> findTeamAllInfo(int teamId);

}
