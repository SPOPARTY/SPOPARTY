package com.spoparty.api.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.board.entity.Board;
import com.spoparty.api.board.repository.BoardRepository;
import com.spoparty.api.member.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final FileRepository fileRepository;

	public List<Board> findByClubId(Long clubId) {
		// return boardRepository.findByClub_id(clubId, Board.class);
		return null;
	}

	public Board findById(Long id) {
		return boardRepository.findById(id, Board.class).orElse(null);
	}

	public Board registerBoard(Board board) {
		return boardRepository.save(board);
	}

	@Transactional
	public Board updateBoard(Board board) {
		Board data = boardRepository.findById(board.getId()).orElse(null);
		if (!board.getTitle().equals(""))
			data.setTitle(board.getTitle());
		if (!board.getContent().equals(""))
			data.setContent(board.getContent());
		if (board.getFile() != null)
			data.setFile(board.getFile());
		return data;
	}

	@Transactional
	public Board deleteBoard(Long id) {
		Board board = boardRepository.findById(id).orElse(null);
		if (board == null || board.isDeleted())
			return null;
		board.softDelete();
		return board;
	}

}
