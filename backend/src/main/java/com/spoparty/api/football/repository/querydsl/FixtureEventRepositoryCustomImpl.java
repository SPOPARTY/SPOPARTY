package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.FixtureEvent;
import com.spoparty.api.football.entity.QFixture;
import com.spoparty.api.football.entity.QFixtureEvent;
import com.spoparty.api.football.entity.QLineupPlayer;
import com.spoparty.api.football.entity.QPlayer;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QSeasonLeagueTeamPlayer;
import com.spoparty.api.football.entity.QTeam;
import com.spoparty.api.football.response.FixtureEventDTO;
import com.spoparty.api.football.response.QFixtureEventDTO;
// import com.spoparty.api.football.response.QFixtureEventPlayerDTO;
import com.spoparty.api.football.response.QFixtureEventTeamDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FixtureEventRepositoryCustomImpl implements FixtureEventRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private final QFixtureEvent fixtureEvent = QFixtureEvent.fixtureEvent;
	private final QFixture fixture = QFixture.fixture;
	private final QSeasonLeagueTeam seasonTeam = QSeasonLeagueTeam.seasonLeagueTeam;
	private final QTeam team = QTeam.team;
	// private final QSeasonLeagueTeamPlayer seasonPlayer = new QSeasonLeagueTeamPlayer("seasonPlayer");
	// private final QSeasonLeagueTeamPlayer seasonAssist = new QSeasonLeagueTeamPlayer("seasonAssist");
	// private final QPlayer player = new QPlayer("player");
	// private final QPlayer assist = new QPlayer("assist");
	private final QLineupPlayer player = new QLineupPlayer("player");
	private final QLineupPlayer assist = new QLineupPlayer("assist");
	public List<FixtureEventDTO> getFixtureEvent(int fixtureId) {

		BooleanBuilder builder = new BooleanBuilder();

		// return jpaQueryFactory.select(fixtureEvent)
		// 	.from(fixtureEvent)
		// 	.join(fixtureEvent.seasonLeagueTeam, seasonTeam)
		// 	.fetchJoin()
		// 	.leftJoin(seasonTeam.team, team)
		// 	.fetchJoin()
		// 	.leftJoin(fixtureEvent.player, player)
		// 	.fetchJoin()
		// 	.leftJoin(fixtureEvent.assist, assist)
		// 	.fetchJoin()
		// 	.join(fixtureEvent.fixture, fixture)
		// 	.fetchJoin()
		// 	.where(fixture.id.eq((long)fixtureId))
		// 	.orderBy(fixtureEvent.time.asc())
		// 	.fetch();

		// return null;

		return jpaQueryFactory.select(
				new QFixtureEventDTO(
					new QFixtureEventTeamDTO(team.nameKr, team.nameEng, team.logo),
					player.name,
					assist.name,
					fixtureEvent.time.longValue(),
					fixtureEvent.type,
					fixtureEvent.detail
				))
			.from(fixtureEvent)
			.join(fixtureEvent.seasonLeagueTeam, seasonTeam)
			.leftJoin(seasonTeam.team, team)
			.leftJoin(fixtureEvent.player, player)
			.leftJoin(fixtureEvent.assist, assist)
			.join(fixtureEvent.fixture, fixture)
			.where(fixture.id.eq((long)fixtureId))
			.orderBy(fixtureEvent.time.asc())
			.fetch();

	}

}

