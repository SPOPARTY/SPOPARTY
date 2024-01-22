package com.spoparty.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	public Member findByLoginId(String loginId);

	public Member findByMemberId(Long memberId);

}
