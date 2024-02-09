package com.spoparty.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.batch.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {


}
