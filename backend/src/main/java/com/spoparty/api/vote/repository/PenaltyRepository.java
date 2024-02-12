package com.spoparty.api.vote.repository;

import org.springframework.data.repository.CrudRepository;

import com.spoparty.api.vote.domain.Penalty;

public interface PenaltyRepository extends CrudRepository<Penalty, String> {
}
