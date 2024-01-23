package com.spoparty.api.football.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Season;
import com.spoparty.api.football.repository.SeasonRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test")
@RequiredArgsConstructor
public class FootballEntityTestController {

	private final SeasonRepository seasonRepository;
	@GetMapping("/season")
	public ResponseEntity<Season> season() {
		Season season = new Season();
		season.setValue("2001");
		Season season2 = seasonRepository.save(season);
		System.out.println(season.getId());
		System.out.println(season2.getId());

		return new ResponseEntity<Season>(season2, HttpStatusCode.valueOf(200));
	}

}
