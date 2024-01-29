package com.spoparty.api.archive.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.archive.entity.Archive;
import com.spoparty.api.archive.repository.ArchiveRepository;
import com.spoparty.api.archive.repository.projection.ArchiveProjection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArchiveService {

	private final ArchiveRepository archiveRepository;

	public List<ArchiveProjection> getArchiveList(Long clubId) {
		return archiveRepository.findByClub_id(clubId, ArchiveProjection.class);
	}

	public ArchiveProjection getArchive(Long archiveId) {
		return archiveRepository.findById(archiveId, ArchiveProjection.class).orElse(null);
	}

	@Transactional
	public ArchiveProjection updateArchive(Archive archive) {
		return null;
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
