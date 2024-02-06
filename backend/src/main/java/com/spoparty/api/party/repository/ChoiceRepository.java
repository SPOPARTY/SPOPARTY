package com.spoparty.api.party.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.party.domain.VoteOption;

public interface ChoiceRepository extends JpaRepository<VoteOption, Long> {
}
