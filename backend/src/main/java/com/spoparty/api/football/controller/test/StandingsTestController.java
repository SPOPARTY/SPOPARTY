package com.spoparty.api.football.controller.test;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Standings;
import com.spoparty.api.football.repository.LeagueRepository;
import com.spoparty.api.football.repository.StandingsRepository;
import com.spoparty.api.football.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/standings")
@RequiredArgsConstructor
public class StandingsTestController {

	private final StandingsRepository standingsRepository;
	private final SeasonRepository seasonRepository;
	private final LeagueRepository leagueRepository;
	@GetMapping("/save")
	public ResponseEntity<Standings> save() {

		Standings standings = Standings.builder()
			.goalsFor(1)
			.form("WWLDD")
			.draw(3)
			.lose(3)
			.win(3)
			.rank(1)
			.goalsAgainst(4)
			.played(10)
			.points(10)
			.goalDiff(10)
			.build();

		Standings standings2 = standingsRepository.save(standings);
		System.out.println(standings.getId());
		System.out.println(standings2.getId());

		return new ResponseEntity<Standings>(standings2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<Standings> find(long id) {
		Standings standings = standingsRepository.findById(id).orElse(null);

		return new ResponseEntity<Standings>(standings, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Standings>> findAll() {
		List<Standings> standings = standingsRepository.findAll();
		return new ResponseEntity<>(standings, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<Standings>> delete(long id) {
		standingsRepository.deleteById(id);
		// List<Standings> standings = standingsRepository.findAll();
		// return new ResponseEntity<>(standings, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<Standings> dirtycheck(long id, String value) {
	// 	Standings standings = standingsRepository.findById(id).orElse(null);
	// 	standings.set("UnitedKingdom");
	//
	// 	Standings standings2 = standingsRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(standings2, HttpStatus.OK);
	// }

}
