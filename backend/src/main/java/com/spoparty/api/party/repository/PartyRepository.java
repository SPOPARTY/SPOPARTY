package com.spoparty.api.party.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.party.entity.Choice;
import com.spoparty.api.party.entity.Party;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
