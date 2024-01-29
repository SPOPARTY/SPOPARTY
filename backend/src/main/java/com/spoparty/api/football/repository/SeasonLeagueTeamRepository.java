package com.spoparty.api.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.repository.querydsl.SeasonLeagueTeamRepositoryCustom;

@Repository
public interface SeasonLeagueTeamRepository
	extends JpaRepository<SeasonLeagueTeam, Long>, SeasonLeagueTeamRepositoryCustom {
}
