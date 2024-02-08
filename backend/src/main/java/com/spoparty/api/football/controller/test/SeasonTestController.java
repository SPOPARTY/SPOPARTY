package com.spoparty.api.football.controller.test;

import java.util.List;

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
@RequestMapping("/football/test/season")
@RequiredArgsConstructor
public class SeasonTestController {

	private final SeasonRepository seasonRepository;

	@GetMapping("/save")
	public ResponseEntity<Season> save(String value) {
		Season season = new Season(value);
		Season season2 = seasonRepository.save(season);
		// log.info(season.getId());
		// log.info(season2.getId());

		return new ResponseEntity<Season>(season2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<Season> find(long id) {
		Season season = seasonRepository.findById(id).orElse(null);

		// return new ResponseEntity<Season>(season, HttpStatusCode.valueOf(200));
		return null;
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Season>> findAll() {
		List<Season> season = seasonRepository.findAll();
		return new ResponseEntity<>(season, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<Season>> delete(long id) {
		seasonRepository.deleteById(id);
		List<Season> season = seasonRepository.findAll();
		return new ResponseEntity<>(season, HttpStatus.OK);
	}

	@Transactional
	@GetMapping("/dirtycheck-test")
	public ResponseEntity<Season> dirtycheck(long id, String value) {
		Season season = seasonRepository.findById(id).orElse(null);
		season.setValue("2023");

		Season season2 = seasonRepository.findById(id).orElse(null);

		return new ResponseEntity<>(season2, HttpStatus.OK);
	}

}
