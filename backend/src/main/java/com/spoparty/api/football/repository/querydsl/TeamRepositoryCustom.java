package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import com.spoparty.api.football.entity.SeasonLeagueTeam;

public interface TeamRepositoryCustom {

	public List<SeasonLeagueTeam> findTeamByKeyword(String keyword);
}
