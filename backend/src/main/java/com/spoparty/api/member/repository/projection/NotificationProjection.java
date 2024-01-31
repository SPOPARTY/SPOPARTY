package com.spoparty.api.member.repository.projection;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface NotificationProjection {

	Long getId();

	@JsonProperty("memberId")
	Long getMember_id();

	String getTitle();

	String getContent();

	int getState();

	LocalDateTime getCreatedTime();

	LocalDateTime getUpdatedTime();
}
