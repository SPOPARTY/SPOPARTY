package com.spoparty.api.football.repository.querydsl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.spoparty.api.football.entity.Fixture;

public interface FixtureRepositoryCustom {

	public List<Fixture> findFixtureByDate(LocalDateTime start, LocalDateTime end);

}
