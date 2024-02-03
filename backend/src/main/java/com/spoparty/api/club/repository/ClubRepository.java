package com.spoparty.api.club.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.club.entity.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
	<T> Optional<T> findById(Long id, Class<T> type);
}
