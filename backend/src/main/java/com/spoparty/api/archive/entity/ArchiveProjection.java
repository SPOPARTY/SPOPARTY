package com.spoparty.api.archive.entity;

import java.time.LocalDateTime;

import com.spoparty.api.member.entity.File;
import com.spoparty.api.member.entity.MemberProjection;

public interface ArchiveProjection {

	Long getId();

	MemberProjection getMember();

	Long getClub_id();

	String getPartyTitle();

	String getFixtureTitle();

	File getFile();

	LocalDateTime getCreatedTime();

	LocalDateTime getUpdatedTime();

}
