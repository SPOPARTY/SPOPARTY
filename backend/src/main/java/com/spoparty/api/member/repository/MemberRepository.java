package com.spoparty.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	public Member findByLoginId(String loginId);

}
