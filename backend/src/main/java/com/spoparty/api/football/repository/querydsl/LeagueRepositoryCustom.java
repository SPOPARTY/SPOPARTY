package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import com.spoparty.api.football.entity.SeasonLeague;

public interface LeagueRepositoryCustom{

	public List<SeasonLeague> findLeagueByKeyword(String keyword);
}
