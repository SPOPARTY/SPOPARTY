package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QTeam;
import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryCustomImpl implements TeamRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;

	private static final QSeasonLeagueTeam seasonLeagueTeam = new QSeasonLeagueTeam("seasonLeagueTeam");

	private static final QTeam team = new QTeam("team");
	public List<SeasonLeagueTeam> findTeamByKeyword(String keyword){

		return jpaQueryFactory.select(seasonLeagueTeam)
			.from(seasonLeagueTeam)
			.join(seasonLeagueTeam.team, team)
			.fetchJoin()
			.where(team.nameKr.contains(keyword))
			.fetch();
	}
}
