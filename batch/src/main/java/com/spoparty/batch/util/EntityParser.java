package com.spoparty.batch.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.spoparty.batch.entity.SeasonLeague;
import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.scheduler.model.Country;
import com.spoparty.batch.scheduler.model.League;
import com.spoparty.batch.scheduler.model.LeagueResponse;
import com.spoparty.batch.scheduler.model.Leagues;
import com.spoparty.batch.scheduler.model.Season;


@Component
public class EntityParser {
	public SeasonLeague seasonLeagueParser(Long seasonLeagueId, LeagueResponse leagueResponse) {
		Leagues leagues = leagueResponse.getResponse().get(0);
		League league = leagues.getLeague();
		Country country = leagues.getCountry();

		Season season = null;
		for (Season s: leagues.getSeasons()){
			if (s.getYear() == 2023) {
				season = s;
				break;
			}
		}

		com.spoparty.batch.entity.League leagueEntity = com.spoparty.batch.entity.League.builder()
			.id(league.getId())
			.nameKr(league.getName())
			.nameEng(league.getName())
			.logo(league.getLogo())
			.country(country.getName())
			.countryLogo(country.getFlag())
			.type(league.getType())
			.build();

		com.spoparty.batch.entity.Season seasonEntity = com.spoparty.batch.entity.Season.builder()
			.id(2023)
			.value("2023")
			.build();


		return SeasonLeague.builder()
			.id(seasonLeagueId)
			.league(leagueEntity)
			.season(seasonEntity)
			.seasonStartDate(ToLocalDateTime((String)season.getStart()))
			.seasonEndDate(ToLocalDateTime((String)season.getEnd()))
			.build();

	}

	public SeasonLeagueTeam seasonLeagueTeamParser(SeasonLeagueTeam item, TeamResponse)

	private LocalDateTime ToLocalDateTime(String date) {
		String[] dateUnit = date.split("-");

		LocalDate localDate = LocalDate.of(Integer.parseInt(dateUnit[0]),
			Integer.parseInt(dateUnit[1]), Integer.parseInt(dateUnit[2]));

		return LocalDateTime.of(localDate, LocalTime.MIN);
	}
}
