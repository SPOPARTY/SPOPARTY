package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.CheerFixture;
import com.spoparty.api.football.entity.QCheer;
import com.spoparty.api.football.entity.QCheerFixture;
import com.spoparty.api.football.entity.QFixture;
import com.spoparty.api.football.entity.QLeague;
import com.spoparty.api.football.entity.QSeasonLeague;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QTeam;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CheerFixtureRepositoryCustomImpl implements CheerFixtureRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private QCheerFixture cheerFixture = QCheerFixture.cheerFixture;
	private QCheer cheer = QCheer.cheer;

	private QFixture fixture = QFixture.fixture;
	private static final QSeasonLeagueTeam homeSeasonLeagueTeam = new QSeasonLeagueTeam("homeSeasonLeagueTeam");
	private static final QSeasonLeagueTeam awaySeasonLeagueTeam = new QSeasonLeagueTeam("awaySeasonLeagueTeam");
	private static final QSeasonLeague seasonLeague = QSeasonLeague.seasonLeague;
	private static final QLeague league = QLeague.league;

	private static final QTeam homeTeam = new QTeam("homeTeam");
	private static final QTeam awayTeam = new QTeam("awayTeam");

	@Override
	public List<CheerFixture> findEndCheerFixture() {
		return jpaQueryFactory.select(cheerFixture)
			.from(cheerFixture)
			.join(cheerFixture.cheers, cheer)
			.fetchJoin()
			.join(cheerFixture.fixture, fixture)
			.fetchJoin()
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
			.where(fixture.status.eq("done").and(cheerFixture.isDeleted.eq(false)))

			.fetch();
	}

	@Override
	public List<CheerFixture> findCheerFixture() {

		return jpaQueryFactory.select(cheerFixture)
			.from(cheerFixture)
			.join(cheerFixture.fixture, fixture)
			.fetchJoin()
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
			.where(cheerFixture.isDeleted.eq(false))
			.fetch();

	}


	public List<CheerFixture> findCheerFixtureById(Long fixtureId){
		return jpaQueryFactory.select(cheerFixture)
			.from(cheerFixture)
			.join(cheerFixture.fixture, fixture)
			.fetchJoin()
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
			.where(cheerFixture.fixture.id.eq(fixtureId))
			.fetch();
	}
}
