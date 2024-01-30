package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.QFixture;
import com.spoparty.api.football.entity.QFixtureEvent;
import com.spoparty.api.football.entity.QPlayer;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QSeasonLeagueTeamPlayer;
import com.spoparty.api.football.entity.QTeam;
import com.spoparty.api.football.response.FixtureEventDTO;
import com.spoparty.api.football.response.QFixtureEventDTO;
import com.spoparty.api.football.response.QFixtureEventPlayerDTO;
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
	private final QSeasonLeagueTeamPlayer seasonPlayer = new QSeasonLeagueTeamPlayer("seasonPlayer");
	private final QSeasonLeagueTeamPlayer seasonAssist = new QSeasonLeagueTeamPlayer("seasonAssist");
	private final QPlayer player = new QPlayer("player");
	private final QPlayer assist = new QPlayer("assist");

	public List<FixtureEventDTO> getFixtureEvent(int fixtureId) {

		BooleanBuilder builder = new BooleanBuilder();

		// return jpaQueryFactory.select(
		// 		new QFixtureEventDTO(
		// 			new QFixtureEventTeamDTO(team.nameKr, team.nameEng, team.logo),
		// 			new QFixtureEventPlayerDTO(player.nameKr, player.nameEng, player.photo),
		// 			new QFixtureEventPlayerDTO(assist.nameKr, assist.nameEng, assist.photo),
		// 			fixtureEvent.time.longValue(),
		// 			fixtureEvent.type,
		// 			fixtureEvent.detail
		// 		))
		// 	.from(fixtureEvent)
		// 	.join(fixtureEvent.fixture, fixture)
		// 	.join(fixtureEvent.seasonLeagueTeam, seasonTeam)
		// 	.join(seasonTeam.team, team)
		// 	.join(fixtureEvent.player, seasonPlayer)
		// 	.join(seasonPlayer.player, player)
		// 	.leftJoin(fixtureEvent.assist, seasonAssist)
		// 	.leftJoin(seasonAssist.player, assist)
		// 	.where(fixture.id.eq((long)fixtureId))
		// 	.orderBy(fixtureEvent.time.asc())
		// 	.fetch();

		// return null;

		return jpaQueryFactory.select(
				new QFixtureEventDTO(
					new QFixtureEventTeamDTO(team.nameKr, team.nameEng, team.logo),
					new QFixtureEventPlayerDTO(player.nameKr, player.nameEng, player.photo),
					new QFixtureEventPlayerDTO(assist.nameKr, assist.nameEng, assist.photo),
					fixtureEvent.time.longValue(),
					fixtureEvent.type,
					fixtureEvent.detail
				))
			.from(fixtureEvent)
			.join(fixtureEvent.fixture, fixture)
			.join(fixtureEvent.seasonLeagueTeam, seasonTeam)
			.join(seasonTeam.team, team)
			.join(fixtureEvent.player, seasonPlayer).on(seasonPlayer.id.eq(fixtureEvent.player.id))
			.join(seasonPlayer.player, player)
			.leftJoin(fixtureEvent.assist, seasonAssist).on(seasonAssist.id.eq(fixtureEvent.assist.id))
			.leftJoin(seasonAssist.player, assist)
			.where(fixture.id.eq((long)fixtureId))
			.orderBy(fixtureEvent.time.asc())
			.fetch();

	}

}

