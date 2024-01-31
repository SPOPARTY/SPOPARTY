package com.spoparty.api.archive.repository.projection;

import java.time.LocalDateTime;

import com.spoparty.api.member.entity.File;
import com.spoparty.api.member.entity.Member;

public interface ArchiveProjection {

	Long getId();

	Member getMember();

	Long getClub_id();

	String getPartyTitle();

	String getFixtureTitle();

	File getFile();

	LocalDateTime getCreatedTime();

	LocalDateTime getUpdatedTime();

}
