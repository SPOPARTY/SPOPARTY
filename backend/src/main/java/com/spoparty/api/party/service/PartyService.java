package com.spoparty.api.party.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.spoparty.api.member.entity.File;
import com.spoparty.api.party.dto.request.PartyUpdateRequestDto;
import com.spoparty.api.party.dto.response.PartyResponseDTO;
import com.spoparty.api.party.dto.response.RecordingResponseDTO;
import com.spoparty.api.party.entity.PartyMemberProjection;
import com.spoparty.security.model.PrincipalDetails;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Recording;

public interface PartyService {

	PartyResponseDTO createParty(PrincipalDetails principalDetails, Long clubId) throws
		OpenViduJavaClientException,
		OpenViduHttpException;

	PartyResponseDTO findParty(Long partyId);

	PartyResponseDTO updateParty(Long partyId, PartyUpdateRequestDto partyUpdateRequestDto);

	Long deleteParty(Long partyId, Long clubId);

	List<PartyMemberProjection> findAllPartyMembers(Long partyId);

	PartyMemberProjection createPartyMember(PrincipalDetails principalDetails, Long clubId, Long partyId) throws
		OpenViduJavaClientException,
		OpenViduHttpException;

	<T> T findPartyMember(Long partyMemberId, Class<T> type);

	Long deletePartyMember(Long partyId, Long partyMemberId, Long clubId);

	Recording startRecording(String sessionId, Map<String, Object> params) throws
			OpenViduJavaClientException,
			OpenViduHttpException;

	void stopRecording(String recordingId) throws
			OpenViduJavaClientException,
			OpenViduHttpException;
	RecordingResponseDTO uploadRecording(String recordingId) ;
	void deleteRecording(String recordingId) throws IOException;
}
