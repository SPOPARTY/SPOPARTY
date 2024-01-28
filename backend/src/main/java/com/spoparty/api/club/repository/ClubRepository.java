package com.spoparty.api.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.club.entity.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
