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
@RequestMapping("/football/teams")
@RequiredArgsConstructor

public class TeamController {
	private final TeamServiceImpl teamServiceImpl;
	private final Common common;

	@GetMapping
	ResponseEntity getTeamAllInfo(int teamId, @AuthenticationPrincipal
	PrincipalDetails principalDetails) {
		ResponseDTO responseDTO = teamServiceImpl.getTeamAllInfo(teamId, principalDetails);

		HttpStatusCode code = common.getStatusByContent(responseDTO);

		return new ResponseEntity<>(responseDTO, code);

	}

}
