package com.spoparty.api.board.repository.projection;

import com.spoparty.api.member.entity.File;
import com.spoparty.api.member.entity.Member;

public interface BoardProjection {

	Long getId();

	Member getMember();

	Long getClub_id();

	String getTitle();

	String getContent();

	File getFile();

}
