package com.spoparty.api.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.CheerFixture;
import com.spoparty.api.football.repository.querydsl.CheerFixtureRepositoryCustom;

@Repository
public interface CheerFixtureRepository extends JpaRepository<CheerFixture, Long>, CheerFixtureRepositoryCustom {


}
