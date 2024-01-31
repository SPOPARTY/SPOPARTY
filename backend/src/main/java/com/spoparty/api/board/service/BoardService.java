package com.spoparty.api.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.board.entity.Board;
import com.spoparty.api.board.entity.BoardProjection;
import com.spoparty.api.board.repository.BoardRepository;
import com.spoparty.api.member.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final FileService fileService;

	public List<BoardProjection> findByClubId(Long clubId) {
		return boardRepository.findByClub_id(clubId, BoardProjection.class);
	}

	public BoardProjection findById(Long id) {
		return boardRepository.findById(id, BoardProjection.class).orElse(null);
	}

	public Board registerBoard(Board board) {
		return boardRepository.save(board);
	}

	@Transactional
	public Board updateBoard(Board board) {
		Board data = boardRepository.findById(board.getId()).orElse(null);
		if (data == null)
			return null;
		if (data.getFile() != null)
			fileService.findById(data.getFile().getId()).softDelete();
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
