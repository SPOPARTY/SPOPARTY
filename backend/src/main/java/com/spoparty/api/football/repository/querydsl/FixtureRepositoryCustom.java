package com.spoparty.api.football.repository.querydsl;

import java.time.LocalDateTime;
import java.util.List;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.response.PartyFixtureDTO;

public interface FixtureRepositoryCustom {

	public List<Fixture> findFixtureByDate(LocalDateTime start, LocalDateTime end);

	public List<Fixture> findFixtureByNext(LocalDateTime now, int count);

	public List<Fixture> findFixtureByLeague(String keyword);

	public List<Fixture> findFixtureByTeam(String keyword);

	public PartyFixtureDTO findPartyFixture(long fixtureId);

}
