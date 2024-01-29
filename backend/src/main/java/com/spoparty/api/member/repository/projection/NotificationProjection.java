package com.spoparty.api.member.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface NotificationProjection {

	Long getId();

	@JsonProperty("memberId")
	Long getMember_id();

	String getTitle();

	String getContent();

	int getState();
}
