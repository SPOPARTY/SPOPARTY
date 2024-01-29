package com.spoparty.api.party.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.party.entity.Choice;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
