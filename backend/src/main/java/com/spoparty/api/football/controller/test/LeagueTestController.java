package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.League;
import com.spoparty.api.football.entity.LeagueType;
import com.spoparty.api.football.entity.League;
import com.spoparty.api.football.repository.LeagueRepository;
import com.spoparty.api.football.repository.LeagueRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/league")
@RequiredArgsConstructor
public class LeagueTestController {

	private final LeagueRepository leagueRepository;
	@GetMapping("/save")
	public ResponseEntity<League> save() {

		League league = League.builder()
			.id(40)
			.nameKr("챔피언십")
			.nameEng("Championship")
			.logo("https://media.api-sports.io/football/leagues/40.png")
			.country("England")
			.countryLogo("https://media.api-sports.io/flags/gb.svg")
			.type(LeagueType.league)
			.build();

		League league2 = leagueRepository.save(league);
		System.out.println(league.getId());
		System.out.println(league2.getId());
		return new ResponseEntity<League>(league2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<League> find(long id) {
		League league = leagueRepository.findById(id).orElse(null);

		return new ResponseEntity<League>(league, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<League>> findAll() {
		List<League> league = leagueRepository.findAll();
		return new ResponseEntity<>(league, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<League>> delete(long id) {
		leagueRepository.deleteById(id);
		// List<League> league = leagueRepository.findAll();
		// return new ResponseEntity<>(league, HttpStatus.OK);
		return null;
	}

	@Transactional
	@GetMapping("/dirtycheck-test")
	public ResponseEntity<League> dirtycheck(long id, String value) {
		League league = leagueRepository.findById(id).orElse(null);
		league.setCountry("UnitedKingdom");

		League league2 = leagueRepository.findById(id).orElse(null);

		return new ResponseEntity<>(league2, HttpStatus.OK);
	}

}
