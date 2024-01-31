package com.spoparty.api.party.service;

import java.util.List;

import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.party.dto.PartyCreateRequestDto;
import com.spoparty.api.party.dto.PartyMemberRequestDto;
import com.spoparty.api.party.dto.PartyUpdateRequestDto;
import com.spoparty.api.party.repository.projection.PartyMemberProjection;
import com.spoparty.api.party.repository.projection.PartyProjection;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

public interface PartyService {

	PartyProjection createParty(PartyCreateRequestDto partyRequestDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException;

	<T> T findParty(Long partyId, Class<T> type);

	PartyProjection updateParty(Long partyId, PartyUpdateRequestDto partyUpdateRequestDto);

	Long deleteParty(Long partyId);

	List<PartyMemberProjection> findAllPartyMembers(Long partyId);

	String createPartyMember(Long partyId, PartyMemberRequestDto partyMemberRequestDto, RoleType role) ;

	<T> T findPartyMember(Long partyMemberId, Class<T> type);

	Long deletePartyMember(Long partyMemberId);
}
