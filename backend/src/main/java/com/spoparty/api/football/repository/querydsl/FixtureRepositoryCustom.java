package com.spoparty.api.football.repository.querydsl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.spoparty.api.football.entity.Fixture;

public interface FixtureRepositoryCustom {
	List<Fixture> findByStartTime(LocalDateTime time);

	// List<Fixture> findByStartTime(LocalDate date);
}
