package com.spoparty.api.board.entity;

import java.time.LocalDateTime;

import com.spoparty.api.member.entity.File;
import com.spoparty.api.member.entity.MemberProjection;

public interface BoardProjection {

	Long getId();

	MemberProjection getMember();

	Long getClub_id();

	String getTitle();

	String getContent();

	File getFile();

	LocalDateTime getCreatedTime();

	LocalDateTime getUpdatedTime();

}
