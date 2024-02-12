package com.spoparty.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.batch.entity.FixtureEvent;

public interface FixtureEventRepository extends JpaRepository<FixtureEvent, Long> {

	List<FixtureEvent> findByFixture_IdAndTime(Long fixtureId, Long time);

	void deleteAllByFixture_Id(Long id);

}
