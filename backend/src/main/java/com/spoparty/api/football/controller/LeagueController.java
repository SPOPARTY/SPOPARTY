package com.spoparty.api.football.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.service.LeagueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/football/leagues")
@RequiredArgsConstructor
public class LeagueController {

	private final LeagueService leagueService;

	@GetMapping
	ResponseEntity findAllLeague() {
		ResponseDTO responseDTO = leagueService.findAllLeague();

		return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/rank/{leagueId}")
	ResponseEntity showLeagueByTeamRank(@PathVariable Optional<Integer> leagueId) {

		ResponseDTO responseDTO = null;
		try {
			responseDTO = leagueService.findTeamRank(leagueId.get());

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(
				responseDTO.toDTO(null, "PathVariable을 확인하세요"),
				HttpStatusCode.valueOf(400));
		}

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
