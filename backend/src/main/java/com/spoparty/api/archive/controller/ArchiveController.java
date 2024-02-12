package com.spoparty.api.archive.controller;

import static com.spoparty.api.common.constants.ErrorCode.*;
import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spoparty.api.archive.entity.Archive;
import com.spoparty.api.archive.entity.ArchiveProjection;
import com.spoparty.api.archive.service.ArchiveService;
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
@RequestMapping("/archives")
public class ArchiveController {

	private final ArchiveService archiveService;
	private final MemberService memberService;
	private final ClubRepository clubRepository;
	private final FileService fileService;

	@GetMapping("/clubs/{clubId}")
	public ResponseEntity<?> getArchiveList(@PathVariable("clubId") Long clubId) {
		List<ArchiveProjection> list = archiveService.getArchiveList(clubId);
		return ApiResponse.success(GET_SUCCESS, list);
	}

	@GetMapping("/{archiveId}")
	public ResponseEntity<?> getArchive(@PathVariable("archiveId") Long archiveId) {
		ArchiveProjection archive = archiveService.getArchive(archiveId);
		return ApiResponse.success(GET_SUCCESS, archive);
	}

	@PostMapping
	public ResponseEntity<?> registerArchive(@RequestBody Map<String, String> request) {
		Long memberId = Long.parseLong(request.get("memberId"));
		Long clubId = Long.parseLong(request.get("clubId"));
		Long fileId = Long.parseLong(request.get("fileId"));
		Long thumbnailId = Long.parseLong(request.get("thumbnailId"));
		String partyTitle = request.get("partyTitle");
		String fixtureTitle = request.get("fixtureTitle");

		Archive archive = new Archive();
		archive.setMember(memberService.findById(memberId));
		archive.setClub(clubRepository.findById(clubId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND)));
		archive.setPartyTitle(partyTitle);
		archive.setFixtureTitle(fixtureTitle);
		archive.setFile(fileService.findById(fileId));
		archive.setThumbnail(fileService.findById(thumbnailId));

		ArchiveProjection data = archiveService.registerArchive(archive);
		return ApiResponse.success(CREATE_SUCCESS, data);
	}

	@PutMapping
	public ResponseEntity<?> updateArchive(Long archiveId, String partyTitle, String fixtureTitle, MultipartFile file) {
		Archive archive = new Archive();
		archive.setId(archiveId);
		archive.setPartyTitle(partyTitle);
		archive.setFixtureTitle(fixtureTitle);
		if (file != null) {
			archive.setFile(fileService.uploadFile(file));
		}
		ArchiveProjection data = archiveService.updateArchive(archive);
		return ApiResponse.success(UPDATE_SUCCESS, data);
	}

	@DeleteMapping("/{archiveId}")
	public ResponseEntity<?> deleteArchive(@PathVariable("archiveId") Long archiveId) {
		archiveService.deleteArchive(archiveId);
		return ApiResponse.success(DELETE_SUCCESS);
	}

}
