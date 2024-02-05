package com.spoparty.api.party.service;

import java.util.List;

import com.spoparty.api.party.dto.request.PartyCreateRequestDto;
import com.spoparty.api.party.dto.request.PartyMemberRequestDto;
import com.spoparty.api.party.dto.response.PartyResponseDTO;
import com.spoparty.api.party.dto.request.PartyUpdateRequestDto;
import com.spoparty.api.party.entity.PartyMemberProjection;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

public interface PartyService {

	PartyResponseDTO createParty(PartyCreateRequestDto partyRequestDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException;

	PartyResponseDTO findParty(Long partyId);

	PartyResponseDTO updateParty(Long partyId, PartyUpdateRequestDto partyUpdateRequestDto);

	Long deleteParty(Long partyId, Long clubId);

	List<PartyMemberProjection> findAllPartyMembers(Long partyId);

	PartyMemberProjection createPartyMember(Long partyId, PartyMemberRequestDto partyMemberRequestDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException;

	<T> T findPartyMember(Long partyMemberId, Class<T> type);

	Long deletePartyMember(Long partyMemberId);
}
