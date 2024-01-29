package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.QSeasonLeague;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QStandings;
import com.spoparty.api.football.entity.QTeam;
import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeasonLeagueTeamRepositoryCustomImpl implements SeasonLeagueTeamRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	private static final QSeasonLeague seasonLeague = QSeasonLeague.seasonLeague;
	private static final QSeasonLeagueTeam seasonLeagueTeam = QSeasonLeagueTeam.seasonLeagueTeam;
	private static final QTeam team = QTeam.team;
	private static final QStandings standing = QStandings.standings;

	public List<SeasonLeagueTeam> findTeamByKeyword(String keyword) {

		return jpaQueryFactory.select(seasonLeagueTeam)
			.from(seasonLeagueTeam)
			.join(seasonLeagueTeam.team, team)
			.fetchJoin()
			.where(team.nameKr.contains(keyword))
			.fetch();
	}

	public List<SeasonLeagueTeam> findTeamRank(int leagueId) {
		return jpaQueryFactory.select(seasonLeagueTeam)
			.from(seasonLeagueTeam)
			.join(seasonLeagueTeam.seasonLeague, seasonLeague)
			.fetchJoin()
			.join(seasonLeagueTeam.team, team)
			.fetchJoin()
			.join(seasonLeagueTeam.standing, standing)
			.fetchJoin()
			.where(seasonLeagueTeam.seasonLeague.id.eq((long)leagueId))
			.orderBy(standing.rank.asc())
			.fetch();
	}

}
