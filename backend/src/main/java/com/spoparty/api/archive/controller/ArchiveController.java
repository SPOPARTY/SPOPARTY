package com.spoparty.api.archive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.archive.entity.Archive;
import com.spoparty.api.archive.service.ArchiveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/archives")
public class ArchiveController {

	private final ArchiveService archiveService;

	@GetMapping("/clubs/{clubId}")
	public ResponseEntity<?> getArchiveList(@PathVariable("clubId") Long clubId) {
		return null;
	}

	@GetMapping("/{archiveId}")
	public ResponseEntity<?> getArchive(@PathVariable("archiveId") Long archiveId) {
		return null;
	}

	@PostMapping
	public ResponseEntity<?> registerArchive(@RequestBody Archive archive) {
		return null;
	}

	@DeleteMapping("/{archiveId}")
	public ResponseEntity<?> registerArchive(@PathVariable("archiveId") Long archiveId) {
		return null;
	}

}
