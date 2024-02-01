package com.spoparty.api.party.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PartyMemberProjection {
	@JsonProperty("participantId")
	Long getId();

	@JsonProperty("memberNickname")
	String getMember_nickname();

	@JsonProperty("role")
	String getRole();

	String getOpenviduToken();

}