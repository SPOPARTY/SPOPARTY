package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.QLeague;
import com.spoparty.api.football.entity.QSeasonLeague;
import com.spoparty.api.football.entity.SeasonLeague;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class LeagueRepositoryImpl implements LeagueRepositoryCustom{


	private final JPAQueryFactory jpaQueryFactory;

	private static final QSeasonLeague seasonLeague = QSeasonLeague.seasonLeague;

	private static final QLeague league = QLeague.league;

	public List<SeasonLeague> findLeagueByKeyword(String keyword){

		return jpaQueryFactory.select(seasonLeague)
			.from(seasonLeague)
			.join(seasonLeague.league, league)
			.fetchJoin()
			.where(league.nameKr.contains(keyword))
			.fetch();
	}
}
