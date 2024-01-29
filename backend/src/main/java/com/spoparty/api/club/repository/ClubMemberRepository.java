package com.spoparty.api.club.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.entity.ClubMember;
import com.spoparty.api.member.entity.Member;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	Optional<ClubMember> findByClubAndMember(Club club, Member member);

	List<ClubMember> findAllByMember(Member member);
}
