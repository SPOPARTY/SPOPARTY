package com.spoparty.api.football.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.service.TeamServiceImpl;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/football/teams")
@RequiredArgsConstructor

public class TeamController {
	private final TeamServiceImpl teamServiceImpl;

	@GetMapping
	ResponseEntity getTeamAllInfo(int teamId,  @AuthenticationPrincipal
	PrincipalDetails principalDetails) {
		ResponseDTO responseDTO = teamServiceImpl.getTeamAllInfo(teamId, principalDetails);

		HttpStatusCode code = getStatusByContent(responseDTO);

		return new ResponseEntity<>(responseDTO, code);

	}

	private HttpStatusCode getStatusByContent(ResponseDTO responseDTO) {
		if (responseDTO.getData() == null) {
			return HttpStatusCode.valueOf(404);
		} else {
			return HttpStatusCode.valueOf(200);
		}
	}

}
