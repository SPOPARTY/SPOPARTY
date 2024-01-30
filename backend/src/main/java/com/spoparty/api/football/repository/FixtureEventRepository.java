package com.spoparty.api.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.FixtureEvent;
import com.spoparty.api.football.repository.querydsl.FixtureEventRepositoryCustom;

@Repository
public interface FixtureEventRepository extends JpaRepository<FixtureEvent, Long>, FixtureEventRepositoryCustom {
}
