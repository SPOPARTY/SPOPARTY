package com.spoparty.api.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.member.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	<T> List<T> findByMember_idOrderByState(Long memberId, Class<T> type);

	<T> Optional<T> findById(Long id, Class<T> type);

}
