package com.spoparty.api.archive.controller;

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

import com.spoparty.api.archive.entity.Archive;
import com.spoparty.api.archive.repository.projection.ArchiveProjection;
import com.spoparty.api.archive.service.ArchiveService;
import com.spoparty.api.club.repository.ClubRepository;
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
		return ResponseEntity.status(200).body(list);
	}

	@GetMapping("/{archiveId}")
	public ResponseEntity<?> getArchive(@PathVariable("archiveId") Long archiveId) {
		ArchiveProjection archive = archiveService.getArchive(archiveId);
		if (archive == null)
			return ResponseEntity.status(404).body(null);
		else
			return ResponseEntity.status(200).body(archive);
	}

	@PostMapping
	public ResponseEntity<?> registerArchive(Long memberId, Long clubId, String partyTitle, String fixtureTitle,
		MultipartFile file) {
		Archive archive = new Archive();
		archive.setMember(memberService.findById(memberId));
		archive.setClub(clubRepository.findById(clubId).orElse(null));
		archive.setPartyTitle(partyTitle);
		archive.setFixtureTitle(fixtureTitle);
		try {
			archive.setFile(fileService.uploadFile(file));
		} catch (IOException e) {
			return ResponseEntity.status(400).body(null);
		}
		ArchiveProjection data = archiveService.registerArchive(archive);
		return ResponseEntity.status(201).body(data);
	}

	@PutMapping
	public ResponseEntity<?> updateArchive(Long archiveId, String partyTitle, String fixtureTitle, MultipartFile file) {
		Archive archive = new Archive();
		archive.setId(archiveId);
		archive.setPartyTitle(partyTitle);
		archive.setFixtureTitle(fixtureTitle);
		try {
			archive.setFile(fileService.uploadFile(file));
		} catch (IOException e) {
			return ResponseEntity.status(400).body(null);
		}
		ArchiveProjection data = archiveService.updateArchive(archive);
		if (data == null)
			return ResponseEntity.status(400).body(null);
		else
			return ResponseEntity.status(200).body(data);
	}

	@DeleteMapping("/{archiveId}")
	public ResponseEntity<?> deleteArchive(@PathVariable("archiveId") Long archiveId) {
		ArchiveProjection archive = archiveService.deleteArchive(archiveId);
		if (archive == null)
			return ResponseEntity.status(400).body(null);
		else
			return ResponseEntity.status(200).body(null);
	}

}