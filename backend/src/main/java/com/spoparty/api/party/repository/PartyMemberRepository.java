package com.spoparty.api.party.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.party.entity.PartyMember;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {
	<T> Optional<T> findById(Long id, Class<T> type);

	<T> List<T> findAllByParty_Id(Long partyId, Class<T> type);
}
