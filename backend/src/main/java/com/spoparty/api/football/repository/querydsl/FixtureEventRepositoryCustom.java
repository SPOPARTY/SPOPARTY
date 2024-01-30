package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import com.spoparty.api.football.entity.FixtureEvent;

public interface FixtureEventRepositoryCustom {
	List<FixtureEvent> getFixtureEvent(Long fixtureId);
}
