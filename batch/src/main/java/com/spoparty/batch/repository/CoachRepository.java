package com.spoparty.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.batch.entity.Coach;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}
