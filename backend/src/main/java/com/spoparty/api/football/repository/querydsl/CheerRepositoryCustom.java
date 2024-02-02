package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import com.spoparty.api.football.entity.Cheer;

public interface CheerRepositoryCustom {
	List<Cheer> findMemberCheer(long memberId);

	long makeCheer(Long memberId, Long teamId, Long cheerFixtureId);
}
