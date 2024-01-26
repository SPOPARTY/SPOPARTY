package com.spoparty.api.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.member.entity.MemberToken;

public interface MemberTokenRepository extends JpaRepository<MemberToken, Long> {

	<T> Optional<T> findById(Long id, Class<T> type);

	<T> Optional<T> findByMember_id(Long memberId, Class<T> type);

	<T> Optional<T> findByRefreshToken(String refreshToken, Class<T> type);

	void deleteByMember_id(Long id);

}
