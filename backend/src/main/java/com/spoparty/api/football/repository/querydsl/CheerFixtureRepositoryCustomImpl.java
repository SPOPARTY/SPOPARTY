package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.CheerFixture;
import com.spoparty.api.football.entity.QCheer;
import com.spoparty.api.football.entity.QCheerFixture;
import com.spoparty.api.football.entity.QFixture;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CheerFixtureRepositoryCustomImpl implements CheerFixtureRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private QCheerFixture cheerFixture = QCheerFixture.cheerFixture;
	private QCheer cheer = QCheer.cheer;

	private QFixture fixture = QFixture.fixture;

	@Override
	public List<CheerFixture> findEndCheerFixture() {
		return jpaQueryFactory.select(cheerFixture)
			.from(cheerFixture)
			.fetch();

	}

	@Override
	public List<CheerFixture> findCheerFixture() {
		return null;
	}
}
