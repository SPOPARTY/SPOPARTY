package com.spoparty.api.football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.Coach;

@Repository
public interface LineupPlyaerRepository extends JpaRepository<Coach, Long> {
}
