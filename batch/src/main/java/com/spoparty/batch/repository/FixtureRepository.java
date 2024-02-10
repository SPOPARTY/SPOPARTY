package com.spoparty.batch.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spoparty.batch.entity.Fixture;


@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Long> {
	<T> T findByStartTimeBetween(LocalDateTime start, LocalDateTime end, Class<T> type);

	<T> Optional<T> findById(Long id, Class<T> type);

	@Query("SELECT f FROM Fixture f WHERE f.id >= :id")
	List<Fixture> findAllWithIdGreaterThanEqual(@Param("id") Long id);

	List<Fixture> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

}
