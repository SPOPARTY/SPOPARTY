package com.spoparty.api.football.repository.querydsl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.entity.QFixture;
import com.spoparty.api.football.entity.QLeague;
import com.spoparty.api.football.entity.QSeasonLeague;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QTeam;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FixtureRepositoryCustomImpl implements FixtureRepositoryCustom{
	private final JPAQueryFactory jpaQueryFactory;

	private static final QFixture fixture = QFixture.fixture;
	private static final QSeasonLeague seasonLeague = QSeasonLeague.seasonLeague;
	private static final QSeasonLeagueTeam  homeSeasonLeagueTeam = new QSeasonLeagueTeam("homeSeasonLeagueTeam");
	private static final QSeasonLeagueTeam  awaySeasonLeagueTeam = new QSeasonLeagueTeam("awaySeasonLeagueTeam");

	private static final QLeague league = QLeague.league;

	private static final QTeam homeTeam = new QTeam("homeTeam");
	private static final QTeam awayTeam = new QTeam("awayTeam");

	@Override
	public List<Fixture> findFixtureByDate(LocalDateTime start, LocalDateTime end){

		return jpaQueryFactory.select(fixture)
			.from(fixture)
			.join(fixture.seasonLeague, seasonLeague)
			.fetchJoin()
			.join(seasonLeague.league, league)
			.fetchJoin()
			.join(fixture.homeTeam, homeSeasonLeagueTeam)
			.fetchJoin()
			.join(homeSeasonLeagueTeam.team, homeTeam)
			.fetchJoin()
			.join(fixture.awayTeam, awaySeasonLeagueTeam)
			.fetchJoin()
			.join(awaySeasonLeagueTeam.team, awayTeam)
			.fetchJoin()
			.where(fixture.startTime.between(start, end))
			.fetch();
	}

}
