package com.spoparty.api.party.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.party.entity.Party;
import com.spoparty.api.party.entity.Penalty;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}
