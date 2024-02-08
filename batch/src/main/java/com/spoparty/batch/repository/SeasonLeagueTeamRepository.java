package com.spoparty.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.batch.entity.SeasonLeagueTeam;

public interface SeasonLeagueTeamRepository extends JpaRepository<SeasonLeagueTeam, Long> {

	SeasonLeagueTeam findByTeam_Id(Long id);

}
