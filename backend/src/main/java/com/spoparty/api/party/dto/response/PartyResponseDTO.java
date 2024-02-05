package com.spoparty.api.party.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.spoparty.api.football.response.PartyFixtureDTO;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.party.entity.Party;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class PartyResponseDTO {
	private Long partyId;
	private String sessionId;
	private String title;
	private Integer maxParticipants;
	private Integer currentParticipants;
	private Long hostId;
	private String hostNickName;
	private String fixtureUrl;
	private PartyFixtureDTO fixtureInfo;

	@QueryProjection
	public PartyResponseDTO(Party party, PartyFixtureDTO partyFixtureDTO, int currentParticipants) {
		this.partyId = party.getId();
		this.sessionId = party.getOpenviduSessionId();
		this.title = party.getTitle();
		this.maxParticipants = party.getMaxParticipants();
		this.currentParticipants = currentParticipants;
		this.fixtureUrl = party.getFixtureUrl();
		this.fixtureInfo = partyFixtureDTO;

		try {
			Member hostMember = party.getHostMember();
			hostId = hostMember.getId();
			hostNickName = hostMember.getNickname();
		} catch (EntityNotFoundException e) {
			log.debug("party.getHostMember() EntityNotFoundException 발생!!!");
			hostId = null;
			hostNickName = null;
		}
	}
}