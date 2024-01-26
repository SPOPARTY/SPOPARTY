package com.spoparty.api.football.repository.querydsl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;

public interface FixtureRepositoryCustom {

	public List<Fixture> findFixtureByDate(LocalDateTime start, LocalDateTime end);

	public List<Fixture> findFixtureByNext(LocalDateTime now, int count);

	public List<Fixture> findFixtureByLeague(String keyword);

	public List<Fixture> findFixtureByTeam(String keyword);




}
