package com.spoparty.api.party.service;

import java.util.List;

import com.spoparty.api.party.dto.PartyMemberRequestDto;
import com.spoparty.api.party.dto.PartyRequestDto;
import com.spoparty.api.party.repository.projection.PartyMemberProjection;
import com.spoparty.api.party.repository.projection.PartyProjection;

public interface PartyService {

	PartyProjection createParty(PartyRequestDto partyRequestDto);

	PartyProjection findParty(Long partyId);

	PartyProjection updateParty(Long partyId, PartyRequestDto partyRequestDto);

	Long deleteParty(Long partyId);

	List<PartyMemberProjection> getAllPartyMembers(Long partyId);

	PartyMemberProjection createPartyMember(Long partyId, PartyMemberRequestDto partyMemberRequestDto);

	PartyMemberProjection getPartyMember(Long partyId, Long memberId);

	Long deletePartyMember(Long partyId, Long memberId);
}
