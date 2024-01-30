package com.spoparty.api.party.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PartyMemberProjection {
	Long getId();

	// @JsonProperty("clubId")
	// Long getClub_id();

	@JsonProperty("memberId")
	Long getMember_id();

	@JsonProperty("memberNickname")
	Long getMember_nickname();

	@JsonProperty("role")
	String getRoleType_name();
}