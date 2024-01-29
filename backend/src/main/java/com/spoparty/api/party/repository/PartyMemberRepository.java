package com.spoparty.api.party.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.party.entity.Party;
import com.spoparty.api.party.entity.PartyMember;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {
}
