package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.FixtureEvent;
import com.spoparty.api.football.entity.QFixture;
import com.spoparty.api.football.entity.QFixtureEvent;
import com.spoparty.api.football.entity.QPlayer;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QSeasonLeagueTeamPlayer;
import com.spoparty.api.football.entity.QTeam;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FixtureEventRepositoryCustomImpl implements FixtureEventRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private final QFixtureEvent fixtureEvent = QFixtureEvent.fixtureEvent;
	private final QFixture fixture = QFixture.fixture;
	private final QSeasonLeagueTeam seasonLeagueTeam = QSeasonLeagueTeam.seasonLeagueTeam;
	private final QTeam team = QTeam.team;
	private final QSeasonLeagueTeamPlayer seasonLeagueTeamPlayer = QSeasonLeagueTeamPlayer.seasonLeagueTeamPlayer;
	private final QPlayer player = QPlayer.player;

	public List<FixtureEvent> getFixtureEvent(Long fixtureId){
		return jpaQueryFactory.select(fixtureEvent)
			.from(fixtureEvent)
			.join(fixture)
			.fetchJoin()
			.join(seasonLeagueTeam)
			.fetchJoin()
			.join(team)
			.fetchJoin()
			.join(seasonLeagueTeam)
			.fetchJoin()
			.join(player)
			.fetchJoin()
			.where(fixture.id.eq(fixtureId))

			.fetch();
	}
}
