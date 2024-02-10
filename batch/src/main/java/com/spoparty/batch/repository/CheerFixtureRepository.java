package com.spoparty.batch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.batch.entity.CheerFixture;

public interface CheerFixtureRepository extends JpaRepository<CheerFixture, Long> {

	<T> Optional<T> findById(Long id, Class<T> type);

	CheerFixture findByFixture_Id(Long id);


}
