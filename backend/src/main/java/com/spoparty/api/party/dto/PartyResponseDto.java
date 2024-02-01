package com.spoparty.api.party.dto;

import com.spoparty.api.football.response.PartyFixtureDTO;
import com.spoparty.api.party.entity.Party;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PartyResponseDto {
	private Long partyId;
	private String sessionId;
	private String title;
	private Integer maxParticipants;
	private Integer currentParticipants;
	private String hostNickName;
	private String fixtureUrl;
	private PartyFixtureDTO fixtureInfo;

	public static PartyResponseDto toDto(Party party, PartyFixtureDTO partyFixtureDTO) {
		return PartyResponseDto.builder()
			.partyId(party.getId())
			.sessionId(party.getOpenviduSessionId())
			.title(party.getTitle())
			.maxParticipants(party.getMaxParticipants())
			.currentParticipants(party.getCurrentParticipants())
			.hostNickName(party.getHost().getNickname())
			.fixtureUrl(party.getFixtureUrl())
			.fixtureInfo(partyFixtureDTO)
			.build();
	}
}