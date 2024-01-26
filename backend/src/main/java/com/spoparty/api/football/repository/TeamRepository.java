package com.spoparty.api.football.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.Team;
import com.spoparty.api.football.repository.querydsl.TeamRepositoryCustom;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, TeamRepositoryCustom {

	<T> Optional<T> findById(Long id, Class<T> type);

}
