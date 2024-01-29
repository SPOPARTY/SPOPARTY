package com.spoparty.api.football.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spoparty.api.football.entity.Cheer;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CheerRepositoryCustomImpl implements CheerRepositoryCustom {
	@Override
	public List<Cheer> findMemberCheer(long memberId) {
		return null;
	}
}
