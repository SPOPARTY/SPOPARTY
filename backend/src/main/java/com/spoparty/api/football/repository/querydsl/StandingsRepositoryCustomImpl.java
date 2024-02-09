package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.QSeasonLeague;
import com.spoparty.api.football.entity.QSeasonLeagueTeam;
import com.spoparty.api.football.entity.QStandings;
import com.spoparty.api.football.entity.QTeam;

import com.spoparty.api.football.response.SeasonLeagueTeamAllInfoDTO;
import com.spoparty.api.football.response.SeasonLeagueTeamStandingDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StandingsRepositoryCustomImpl implements StandingsRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;

	QSeasonLeagueTeam seasonLeagueTeam = QSeasonLeagueTeam.seasonLeagueTeam;
	QTeam team = QTeam.team;
	QStandings standings = QStandings.standings;
	QSeasonLeague seasonLeague = QSeasonLeague.seasonLeague;


	@Override
	public List<SeasonLeagueTeamStandingDTO> findTeamRank(int leagueId) {

		// return jpaQueryFactory.select(new QSeasonLeagueTeamStandingDTO(seasonLeagueTeam.id,
		// 								team.id, team.nameKr, team.nameEng, team.logo,
		// 								new QStandingDTO(standings.rank, standings.points, standings.goalDiff,
		// 									standings.form, standings.played, standings.win, standings.draw, standings.lose, standings.goalsFor,
		// 	standings.goalsAgainst))).from(standings)
		// 	.join(standings.seasonLeagueTeam, seasonLeagueTeam)
		// 	.join(seasonLeagueTeam.team, team)
		// 	.where(seasonLeagueTeam.seasonLeague.id.eq((long)leagueId))
		// 	.fetch();
		return null;
	}

	@Override
	public List<SeasonLeagueTeamAllInfoDTO> findTeamAllInfo(int teamId) {
		return null;
	}

	// public List<SeasonLeagueTeam> findTeamRank(int leagueId) {
	// 	return jpaQueryFactory.select(seasonLeagueTeam)
	// 		.from(seasonLeagueTeam)
	// 		.join(seasonLeagueTeam.seasonLeague, seasonLeague)
	// 		.fetchJoin()
	// 		.join(seasonLeagueTeam.team, team)
	// 		.fetchJoin()
	// 		.join(seasonLeagueTeam.standing, standings)
	// 		.fetchJoin()
	// 		.where(seasonLeagueTeam.seasonLeague.id.eq((long)leagueId))
	// 		.orderBy(standings.rank.asc())
	// 		.fetch();
	// }


	// @Override
	// public List<SeasonLeagueTeam> findTeamAllInfo(int teamId) {
	// 	return jpaQueryFactory.select(seasonLeagueTeam)
	// 		.from(seasonLeagueTeam)
	// 		.join(seasonLeagueTeam.seasonLeagueTeamPlayers, seasonLeagueTeamPlayer)
	// 		.fetchJoin()
	// 		.join(seasonLeagueTeamPlayer.player, player)
	// 		.fetchJoin()
	// 		.join(seasonLeagueTeam.team, team)
	// 		.fetchJoin()
	// 		.join(seasonLeagueTeam.standing, standings)
	// 		.fetchJoin()
	// 		.join(seasonLeagueTeam.coach, coach)
	// 		.fetchJoin()
	// 		.join(seasonLeagueTeamPlayer.player)
	// 		.fetchJoin()
	// 		.where(seasonLeagueTeam.id.eq((long)teamId))
	// 		.fetch();
	// }
}
