package com.spoparty.api.archive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.archive.entity.Archive;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

	<T> Optional<T> findById(Long id, Class<T> type);

	<T> List<T> findByClub_idOrderByCreatedTimeDesc(Long clubId, Class<T> type);

	<T> List<T> findByMember_Id(Long memberId, Class<T> type);

}
