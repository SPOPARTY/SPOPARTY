package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import com.spoparty.api.football.entity.CheerFixture;

public interface CheerFixtureRepositoryCustom {

	List<CheerFixture> findEndCheerFixture();

	List<CheerFixture> findCheerFixture();

}
