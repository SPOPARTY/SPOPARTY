package com.spoparty.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.batch.entity.SeasonLeague;

@Repository
public interface SeasonLeagueRepository extends JpaRepository<SeasonLeague, Long> {
}
