package com.spoparty.api.board.controller;

import java.io.IOException;
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
import com.spoparty.api.board.service.BoardService;
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
	// private final ClubService clubService;

	@GetMapping("/clubs/{clubId}")
	public ResponseEntity<?> getBoardList(@PathVariable("clubId") Long clubId) {
		List<Board> list = boardService.findByClubId(clubId);
		return ResponseEntity.status(200).body(list);
	}

	@GetMapping("/{boardId}")
	public ResponseEntity<?> getBoard(@PathVariable("boardId") Long id) {
		Board board = boardService.findById(id);
		if (board == null)
			return ResponseEntity.status(404).body(null);
		else
			return ResponseEntity.status(200).body(board);
	}

	// new FormData() 로 파라미터와 파일을 같이 날렸을 경우로 가정
	@PostMapping
	public ResponseEntity<?> registerBoard(String title, String content, Long memberId, Long clubId,
		MultipartFile file) {
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setMember(memberService.findById(memberId));
		// board.setClub(clubService.findById(clubId));
		if (file != null) {
			try {
				board.setFile(fileService.uploadFile(file));
			} catch (IOException e) {
				return ResponseEntity.status(400).body(null);
			}
		}
		board = boardService.registerBoard(board);
		if (board == null)
			return ResponseEntity.status(400).body(null);
		else
			return ResponseEntity.status(201).body(board);
	}

	@PutMapping
	public ResponseEntity<?> updateBoard(Long id, String title, String content, MultipartFile file) {
		Board board = new Board();
		board.setId(id);
		board.setTitle(title);
		board.setContent(content);
		if (file != null) {
			try {
				board.setFile(fileService.uploadFile(file));
			} catch (IOException e) {
				return ResponseEntity.status(400).body(null);
			}
		}
		board = boardService.updateBoard(board);
		if (board == null)
			return ResponseEntity.status(400).body(null);
		else
			return ResponseEntity.status(201).body(board);
	}

	@DeleteMapping("/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable("boardId") Long id) {
		Board board = boardService.deleteBoard(id);
		if (board == null)
			return ResponseEntity.status(400).body(null);
		else
			return ResponseEntity.status(200).body(null);
	}

}
