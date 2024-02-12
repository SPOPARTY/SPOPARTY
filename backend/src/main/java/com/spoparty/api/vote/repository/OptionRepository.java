package com.spoparty.api.vote.repository;

import org.springframework.data.repository.CrudRepository;

import com.spoparty.api.vote.domain.Option;

public interface OptionRepository extends CrudRepository<Option, String> {
}
