package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.football.entity.Cheer;
import com.spoparty.api.football.entity.QCheer;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CheerRepositoryCustomImpl implements CheerRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private QCheer cheer = QCheer.cheer;

	@Override
	public List<Cheer> findMemberCheer(long memberId) {
		return jpaQueryFactory.select(cheer)
			.from(cheer)
			.where(cheer.member.id.eq(memberId))
			.fetch();
	}

	@Transactional
	public long makeCheer(int memberId, int teamId, int cheerFixtureId) {
		return jpaQueryFactory.insert(cheer)
			.set(cheer.member.id, (long)memberId)
			.set(cheer.seasonLeagueTeam.id, (long)teamId)
			.set(cheer.cheerFixture.id, (long)cheerFixtureId)
			.execute();

	}
}
