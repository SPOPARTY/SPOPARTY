package com.spoparty.api.party.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PartyMemberProjection {
	@JsonProperty("participantId")
	Long getId();

	// @JsonProperty("memberId")
	// Long getMember_id();

	@JsonProperty("memberNickname")
	Long getMember_nickname();

	@JsonProperty("role")
	String getRoleType_name();
}