package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import com.spoparty.api.football.entity.FixtureEvent;
import com.spoparty.api.football.response.FixtureEventDTO;

public interface FixtureEventRepositoryCustom {
	List<FixtureEventDTO> getFixtureEvent(int fixtureId);
}
