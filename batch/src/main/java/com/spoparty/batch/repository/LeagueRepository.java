package com.spoparty.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.batch.entity.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
}
