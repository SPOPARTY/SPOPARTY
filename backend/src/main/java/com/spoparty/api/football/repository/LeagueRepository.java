package com.spoparty.api.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
}
