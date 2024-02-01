package com.spoparty.api.football.controller;

import static com.spoparty.api.common.constants.SuccessCode.*;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity findCheerFixture(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		cheerServiceImpl.deleteEndCheerFixture();

		ResponseDTO responseDTO = cheerServiceImpl.findCheerFixture(principalDetails, null);

		// HttpStatusCode code = common.getStatusByContent(responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));
	}

	@GetMapping(params = {"fixtureId"})
	public ResponseEntity findCheerFixture(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@RequestParam(value = "fixtureId") Long fixtureId) {

		ResponseDTO responseDTO = cheerServiceImpl.findCheerFixture(principalDetails, fixtureId);

		// HttpStatusCode code = common.getStatusByContent(responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));
	}

	@PostMapping
	public ResponseEntity makeCheer(@AuthenticationPrincipal PrincipalDetails principalDetails, int memberId,
		int teamId, int cheerFixtureId, Long fixtureId) {
		cheerServiceImpl.makeCheer(memberId, teamId, cheerFixtureId);

		cheerServiceImpl.deleteEndCheerFixture();

		ResponseDTO responseDTO = cheerServiceImpl.findCheerFixture(principalDetails, fixtureId);

		responseDTO.changeMessage("응원 정보 생성 성공");

		return new ResponseEntity<>(responseDTO, CHEER_CREATE_SUCCESS.getStatus());
	}

}
