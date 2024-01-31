package com.spoparty.api.archive.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.archive.entity.Archive;
import com.spoparty.api.archive.entity.ArchiveProjection;
import com.spoparty.api.archive.repository.ArchiveRepository;
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
		return archiveRepository.findByClub_id(clubId, ArchiveProjection.class);
	}

	public ArchiveProjection getArchive(Long archiveId) {
		return archiveRepository.findById(archiveId, ArchiveProjection.class).orElse(null);
	}

	public ArchiveProjection registerArchive(Archive archive) {
		archive = archiveRepository.save(archive);
		return archiveRepository.findById(archive.getId(), ArchiveProjection.class).orElse(null);
	}

	@Transactional
	public ArchiveProjection updateArchive(Archive archive) {
		Archive data = archiveRepository.findById(archive.getId(), Archive.class).orElse(null);
		if (data == null)
			return null;
		if (data.getFile() != null) {
			fileService.findById(data.getFile().getId()).softDelete();
		}
		if (!archive.getFixtureTitle().equals("")) {
			data.setFixtureTitle(archive.getFixtureTitle());
		}
		if (!archive.getPartyTitle().equals("")) {
			data.setPartyTitle(archive.getPartyTitle());
		}
		if (archive.getFile() != null) {
			data.setFile(archive.getFile());
		}
		return archiveRepository.findById(archive.getId(), ArchiveProjection.class).orElse(null);
	}

	@Transactional
	public ArchiveProjection deleteArchive(Long archiveId) {
		Archive archive = archiveRepository.findById(archiveId, Archive.class).orElse(null);
		if (archive == null)
			return null;
		else
			archive.softDelete();
		return archiveRepository.findById(archiveId, ArchiveProjection.class).orElse(null);
	}

}
