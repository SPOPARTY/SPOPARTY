package com.spoparty.api.member.repository.projection;

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

	int getStatus();

}
