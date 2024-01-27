package com.spoparty.api.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	// <T> List<T> findByClub_id(Long clubId, Class<T> type);

	<T> Optional<T> findById(Long id, Class<T> type);

}
