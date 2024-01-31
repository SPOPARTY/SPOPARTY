package com.spoparty.api.member.repository.projection;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface MemberProjection {

	Long getId();

	String getLoginId();

	String getLoginPwd();

	String getNickname();

	String getEmail();

	String getProvider();

	String getRoleName();

	@JsonProperty("teamId")
	Long getTeam_id();

	int getState();

	LocalDateTime getCreatedTime();

	LocalDateTime getUpdatedTime();

}
