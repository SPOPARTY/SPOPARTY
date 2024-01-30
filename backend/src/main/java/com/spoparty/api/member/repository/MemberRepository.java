package com.spoparty.api.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spoparty.api.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	// Member 기능
	<T> Optional<T> findById(Long id, Class<T> type);

	<T> Optional<T> findByLoginId(String loginId, Class<T> type);

	<T> Optional<T> findByEmail(String email, Class<T> type);

	<T> Optional<T> findByLoginIdAndEmail(String loginId, String email, Class<T> type);

}
