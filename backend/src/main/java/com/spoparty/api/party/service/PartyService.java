package com.spoparty.api.party.service;

import java.util.List;

import com.spoparty.api.party.dto.PartyCreateRequestDto;
import com.spoparty.api.party.dto.PartyMemberRequestDto;
import com.spoparty.api.party.dto.PartyResponseDto;
import com.spoparty.api.party.dto.PartyUpdateRequestDto;
import com.spoparty.api.party.repository.projection.PartyMemberProjection;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

public interface PartyService {

	PartyResponseDto createParty(PartyCreateRequestDto partyRequestDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException;

	PartyResponseDto findParty(Long partyId);

	PartyResponseDto updateParty(Long partyId, PartyUpdateRequestDto partyUpdateRequestDto);

	Long deleteParty(Long partyId, Long clubId);

	List<PartyMemberProjection> findAllPartyMembers(Long partyId);

	PartyMemberProjection createPartyMember(Long partyId, PartyMemberRequestDto partyMemberRequestDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException;

	<T> T findPartyMember(Long partyMemberId, Class<T> type);

	Long deletePartyMember(Long partyMemberId);
}
