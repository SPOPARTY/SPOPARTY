package com.spoparty.api.board.controller;

import static com.spoparty.api.common.constants.ErrorCode.*;
import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spoparty.api.board.entity.Board;
import com.spoparty.api.board.entity.BoardProjection;
import com.spoparty.api.board.service.BoardService;
import com.spoparty.api.club.repository.ClubRepository;
import com.spoparty.api.common.dto.ApiResponse;
import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.member.service.FileService;
import com.spoparty.api.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

	private final BoardService boardService;
	private final FileService fileService;
	private final MemberService memberService;
	private final ClubRepository clubRepository;

	@GetMapping("/clubs/{clubId}")
	public ResponseEntity<?> getBoardList(@PathVariable("clubId") Long clubId) {
		List<BoardProjection> list = boardService.findByClubId(clubId);
		return ApiResponse.success(GET_SUCCESS, list);
	}

	@GetMapping("/{boardId}")
	public ResponseEntity<?> getBoard(@PathVariable("boardId") Long id) {
		BoardProjection board = boardService.findById(id);
		return ApiResponse.success(GET_SUCCESS, board);
	}

	// new FormData() 로 파라미터와 파일을 같이 날렸을 경우로 가정
	@PostMapping
	public ResponseEntity<?> registerBoard(String title, String content, Long memberId, Long clubId,
		MultipartFile file) {
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setMember(memberService.findById(memberId));
		board.setClub(clubRepository.findById(clubId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND)));
		if (file != null) {
			board.setFile(fileService.uploadFile(file));
		}
		boardService.registerBoard(board);
		BoardProjection data = boardService.findById(board.getId());
		return ApiResponse.success(CREATE_SUCCESS, data);
	}

	@PutMapping
	public ResponseEntity<?> updateBoard(Long id, String title, String content, MultipartFile file) {
		Board board = new Board();
		board.setId(id);
		board.setTitle(title);
		board.setContent(content);
		if (file != null) {
			board.setFile(fileService.uploadFile(file));
		}
		BoardProjection data = boardService.updateBoard(board);
		return ApiResponse.success(UPDATE_SUCCESS, data);
	}

	@DeleteMapping("/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable("boardId") Long id) {
		boardService.deleteBoard(id);
		return ApiResponse.success(DELETE_SUCCESS);
	}

}
