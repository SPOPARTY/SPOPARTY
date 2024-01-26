package com.spoparty.api.football.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.response.FixtureDTO;
import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.service.FootballService;
import com.spoparty.common.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/football")
@RequiredArgsConstructor
public class FootballController {

	private final FootballService footballService;



	@GetMapping("/fixtures")
	ResponseEntity<ResponseDTO<List<FixtureDTO>>> searchFixtureByDate(String date) {
		System.out.println("호출!!");
		ResponseDTO<List<FixtureDTO>> responseDTO = footballService.findFixtureByDate(date);
		System.out.println("*******************************************");
		return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));
	}


}