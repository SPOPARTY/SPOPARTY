package com.spoparty.api.club.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.club.entity.Club;
import com.spoparty.api.club.entity.ClubMember;
import com.spoparty.api.member.entity.Member;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	<T> Optional<T> findById(Long id, Class<T> type);

	Optional<ClubMember> findByClub_IdAndMember_Id(Long clubId, Long memberId);

	List<ClubMember> findAllByMember(Member member);

	List<ClubMember> findAllByClub(Club club);

	List<ClubMember> findAllByClub_Id(Long clubId);

	List<ClubMember> findAllByClub_IdAndMember_IdNot(Long clubId, Long memberId);
}
