package com.spoparty.api.football.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.spoparty.api.common.constants.SuccessCode.*;
import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.service.CheerServiceImpl;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/football/cheers")
public class CheerController {

	private final CheerServiceImpl cheerServiceImpl;
	private final Common common;

	@GetMapping
	@Transactional
	public ResponseEntity findCheerFixture(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		cheerServiceImpl.deleteEndCheerFixture();

		ResponseDTO responseDTO = cheerServiceImpl.findCheerFixture(principalDetails);

		HttpStatusCode code = common.getStatusByContent(responseDTO);

		return new ResponseEntity<>(responseDTO, code);
	}

	@PostMapping
	public ResponseEntity makeCheer(@AuthenticationPrincipal PrincipalDetails principalDetails, int memberId,
		int teamId, int cheerFixtureId) {
		cheerServiceImpl.makeCheer(memberId, teamId, cheerFixtureId);

		cheerServiceImpl.deleteEndCheerFixture();

		ResponseDTO responseDTO = cheerServiceImpl.findCheerFixture(principalDetails);



		return new ResponseEntity<>(responseDTO, CHEER_CREATE_SUCCESS.getStatus());
	}
}
