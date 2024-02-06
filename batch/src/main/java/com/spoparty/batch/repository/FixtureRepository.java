package com.spoparty.batch.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.batch.entity.Fixture;


@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Long> {
	<T> T findByStartTimeBetween(LocalDateTime start, LocalDateTime end, Class<T> type);
}
