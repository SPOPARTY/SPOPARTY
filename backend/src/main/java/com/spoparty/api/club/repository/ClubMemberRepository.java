package com.spoparty.api.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.club.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
}
