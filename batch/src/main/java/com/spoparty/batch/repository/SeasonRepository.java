package com.spoparty.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.batch.entity.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
	<T> T findByValue(String value, Class<T> type);
}
