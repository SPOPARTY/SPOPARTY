package com.spoparty.api.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.Cheer;
import com.spoparty.api.football.repository.querydsl.CheerRepositoryCustom;

@Repository
public interface CheerRepository extends JpaRepository<Cheer, Long>, CheerRepositoryCustom {
}
