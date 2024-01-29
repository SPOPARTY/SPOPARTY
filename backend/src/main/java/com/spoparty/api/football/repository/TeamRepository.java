package com.spoparty.api.football.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

	<T> Optional<T> findById(Long id, Class<T> type);

}
