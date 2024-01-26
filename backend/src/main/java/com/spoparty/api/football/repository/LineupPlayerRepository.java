package com.spoparty.api.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.Coach;
import com.spoparty.api.football.entity.LineupPlayer;

@Repository
public interface LineupPlayerRepository extends JpaRepository<LineupPlayer, Long> {
}
