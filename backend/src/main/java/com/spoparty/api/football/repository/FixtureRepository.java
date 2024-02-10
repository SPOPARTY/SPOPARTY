package com.spoparty.api.football.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.repository.querydsl.FixtureRepositoryCustom;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Long>, FixtureRepositoryCustom {
	List<Fixture> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
	<T> T findById(Long id, Class<T> type);
}
