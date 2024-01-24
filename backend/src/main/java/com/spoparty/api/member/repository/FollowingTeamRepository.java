package com.spoparty.api.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.member.entity.FollowingTeam;

public interface FollowingTeamRepository extends JpaRepository<FollowingTeam, Long> {

	<T> Optional<T> findById(Long id, Class<T> type);

	//
	<T> List<T> findByMember_idAndTeam_id(Long memberId, Long teamId, Class<T> type);

	<T> List<T> findByMember_id(Long memberId, Class<T> type);

}
