package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.CheerFixture;
import com.spoparty.api.football.repository.CheerFixtureRepository;
import com.spoparty.api.football.repository.FixtureRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/cheerFixture")
@RequiredArgsConstructor
public class CheerFixtureTestController {

	private final CheerFixtureRepository cheerFixtureRepository;
	private final FixtureRepository fixtureRepository;

	@GetMapping("/save")
	public ResponseEntity<CheerFixture> save() {

		CheerFixture cheerFixture = CheerFixture.builder()
			// .fixture(fixtureRepository.findById((long)0).orElse(null))
			.awayCount(10)
			.homeCount(20)
			.build();

		CheerFixture cheerFixture2 = cheerFixtureRepository.save(cheerFixture);
		System.out.println(cheerFixture.getId());
		System.out.println(cheerFixture2.getId());

		return new ResponseEntity<CheerFixture>(cheerFixture2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<CheerFixture> find(long id) {
		CheerFixture cheerFixture = cheerFixtureRepository.findById(id).orElse(null);

		return new ResponseEntity<CheerFixture>(cheerFixture, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<CheerFixture>> findAll() {
		List<CheerFixture> cheerFixture = cheerFixtureRepository.findAll();
		return new ResponseEntity<>(cheerFixture, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<CheerFixture>> delete(long id) {
		cheerFixtureRepository.deleteById(id);
		// List<CheerFixture> cheerFixture = cheerFixtureRepository.findAll();
		// return new ResponseEntity<>(cheerFixture, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<CheerFixture> dirtycheck(long id, String value) {
	// 	CheerFixture cheerFixture = cheerFixtureRepository.findById(id).orElse(null);
	// 	cheerFixture.set("UnitedKingdom");
	//
	// 	CheerFixture cheerFixture2 = cheerFixtureRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(cheerFixture2, HttpStatus.OK);
	// }

}
