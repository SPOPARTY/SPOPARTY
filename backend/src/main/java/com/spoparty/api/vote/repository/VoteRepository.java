package com.spoparty.api.vote.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spoparty.api.vote.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, String> {
	List<Vote> findAllByPartyIdAndOngoingOrderByCreatedTimeDesc(String partyId, int ongoing);

	List<Vote> findAllByPartyIdAndUser_userIdOrderByCreatedTimeDesc(String partyId, String userId);
}
