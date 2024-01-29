package com.spoparty.api.football.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.security.model.PrincipalDetails;

public interface TeamService {
	ResponseDTO getTeamAllInfo(int teamId, @AuthenticationPrincipal
	PrincipalDetails principalDetails);


}
