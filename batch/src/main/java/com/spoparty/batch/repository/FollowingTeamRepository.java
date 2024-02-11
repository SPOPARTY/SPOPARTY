package com.spoparty.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.batch.entity.FollowingTeam;

public interface FollowingTeamRepository extends JpaRepository<FollowingTeam, Long> {

	List<FollowingTeam> findByTeam_Id(Long id);


}
