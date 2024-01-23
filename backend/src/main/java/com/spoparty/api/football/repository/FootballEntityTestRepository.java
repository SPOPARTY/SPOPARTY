package com.spoparty.api.football.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FootballEntityTestRepository {

	private final EntityManager em;
}
