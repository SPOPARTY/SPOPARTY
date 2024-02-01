package com.spoparty.api.board.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.board.entity.Board;
import com.spoparty.api.board.entity.BoardProjection;
import com.spoparty.api.board.repository.BoardRepository;
import com.spoparty.api.common.exception.CustomException;
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
		return boardRepository.findById(id, BoardProjection.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
	}

	public void registerBoard(Board board) {
		boardRepository.save(board);
	}

	@Transactional
	public BoardProjection updateBoard(Board board) {
		Board data = boardRepository.findById(board.getId()).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
		if (data.getFile() != null && board.getFile() != null)
			fileService.findById(data.getFile().getId()).softDelete();
		if (!board.getTitle().isEmpty())
			data.setTitle(board.getTitle());
		if (!board.getContent().isEmpty())
			data.setContent(board.getContent());
		if (board.getFile() != null)
			data.setFile(board.getFile());
		return boardRepository.findById(data.getId(), BoardProjection.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
	}

	@Transactional
	public void deleteBoard(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
		board.softDelete();
	}

}
