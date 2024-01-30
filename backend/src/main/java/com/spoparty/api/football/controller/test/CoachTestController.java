package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Coach;
import com.spoparty.api.football.repository.CoachRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/football/test/coach")
@RequiredArgsConstructor
@Slf4j
public class CoachTestController {

	private final CoachRepository coachRepository;
	@GetMapping("/save")
	public ResponseEntity<Coach> save() {

		Coach coach = Coach.builder()
			.id(31)
			.age(30)
			.photo("https://source.unsplash.com/random/300x300?person")
			.nationality("Germany")
			.nameKr("코치2")
			.nameEng("coach Two")
			.build();

		Coach coach2 = coachRepository.save(coach);
		// log.info(coach.getId());
		// log.info(coach2.getId());

		return new ResponseEntity<Coach>(coach2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<Coach> find(long id) {
		Coach coach = coachRepository.findById(id).orElse(null);

		return new ResponseEntity<Coach>(coach, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Coach>> findAll() {
		List<Coach> coach = coachRepository.findAll();
		return new ResponseEntity<>(coach, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<Coach>> delete(long id) {
		coachRepository.deleteById(id);
		// List<Coach> coach = coachRepository.findAll();
		// return new ResponseEntity<>(coach, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<Coach> dirtycheck(long id, String value) {
	// 	Coach coach = coachRepository.findById(id).orElse(null);
	// 	coach.set("UnitedKingdom");
	//
	// 	Coach coach2 = coachRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(coach2, HttpStatus.OK);
	// }

}
