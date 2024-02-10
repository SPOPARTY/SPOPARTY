package com.spoparty.api.party.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PartyMemberProjection {
	@JsonProperty("participantId")
	Long getId();

	@JsonProperty("memberId")
	Long getMember_Id();

	@JsonProperty("memberNickname")
	String getMember_nickname();

	@JsonProperty("role")
	String getRole();

	String getOpenviduToken();

}