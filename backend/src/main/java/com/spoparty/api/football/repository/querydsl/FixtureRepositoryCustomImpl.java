package com.spoparty.api.football.repository.querydsl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.entity.QFixture;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FixtureRepositoryCustomImpl implements FixtureRepositoryCustom{
	private final JPAQueryFactory jpaQueryFactory;

	private static final QFixture fixture = QFixture.fixture;

	@Override
	public List<Fixture> findByStartTime(LocalDateTime time) {
		return jpaQueryFactory
			.selectFrom(fixture)
			.where(fixture.startTime.eq(time))
			.fetch()
			;
	}
//
// 	@Override
// 	public List<Fixture> findByStartTime(LocalDate date) {
// 		return jpaQueryFactory
// 			.selectFrom(fixture)
// 			.where("DATE({0})", fixture.startTime.eq(date))
// 			.fetch();
// 	}
}
