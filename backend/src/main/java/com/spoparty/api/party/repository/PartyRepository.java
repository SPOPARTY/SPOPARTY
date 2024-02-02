package com.spoparty.api.party.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.party.entity.Party;

public interface PartyRepository extends JpaRepository<Party, Long> {
	<T> Optional<T> findById(Long id, Class<T> type);
}
