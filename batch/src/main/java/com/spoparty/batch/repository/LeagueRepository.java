package com.spoparty.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.batch.entity.League;

public interface LeagueRepository extends JpaRepository<League, Long> {

}
