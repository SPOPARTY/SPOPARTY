package com.spoparty.api.archive.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.archive.entity.Archive;
import com.spoparty.api.archive.entity.ArchiveProjection;
import com.spoparty.api.archive.repository.ArchiveRepository;
import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.member.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArchiveService {

	private final ArchiveRepository archiveRepository;
	private final FileService fileService;

	public List<ArchiveProjection> getArchiveList(Long clubId) {
		return archiveRepository.findByClub_idOrderByCreatedTimeDesc(clubId, ArchiveProjection.class);
	}

	public ArchiveProjection getArchive(Long archiveId) {
		return archiveRepository.findById(archiveId, ArchiveProjection.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
	}

	public ArchiveProjection registerArchive(Archive archive) {
		archive = archiveRepository.save(archive);
		return archiveRepository.findById(archive.getId(), ArchiveProjection.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
	}

	@Transactional
	public ArchiveProjection updateArchive(Archive archive) {
		Archive data = archiveRepository.findById(archive.getId(), Archive.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
		if (data.getFile() != null && archive.getFile() != null) {
			fileService.deleteFile(data.getFile().getId());
		}
		if (!archive.getFixtureTitle().isEmpty()) {
			data.setFixtureTitle(archive.getFixtureTitle());
		}
		if (!archive.getPartyTitle().isEmpty()) {
			data.setPartyTitle(archive.getPartyTitle());
		}
		if (archive.getFile() != null) {
			data.setFile(archive.getFile());
		}
		return archiveRepository.findById(archive.getId(), ArchiveProjection.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
	}

	@Transactional
	public void deleteArchive(Long archiveId) {
		Archive archive = archiveRepository.findById(archiveId, Archive.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
		if (archive.getFile() != null) {
			fileService.deleteFile(archive.getFile().getId());
		}
		if (archive.getThumbnail() != null) {
			fileService.deleteFile(archive.getThumbnail().getId());
		}
		archive.softDelete();
	}

}
