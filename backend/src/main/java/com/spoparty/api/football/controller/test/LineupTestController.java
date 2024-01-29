package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Lineup;
import com.spoparty.api.football.repository.LineupRepository;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamPlayerRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/lineup")
@RequiredArgsConstructor
public class LineupTestController {

	private final LineupRepository lineupRepository;
	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	private final FixtureRepository fixtureRepository;
	private final SeasonLeagueTeamPlayerRepository seasonLeagueTeamPlayerRepository;
	@GetMapping("/save")
	public ResponseEntity<Lineup> save() {





		Lineup lineup = Lineup.builder()
			.fixture(fixtureRepository.findById((long)0).orElse(null))
			.seasonLeagueTeam(seasonLeagueTeamRepository.findById((long)2).orElse(null))
			.formation("2-3-3")
			.build();

		Lineup lineup2 = lineupRepository.save(lineup);
		System.out.println(lineup.getId());
		System.out.println(lineup2.getId());

		return new ResponseEntity<Lineup>(lineup2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<Lineup> find(long id) {
		Lineup lineup = lineupRepository.findById(id).orElse(null);

		return new ResponseEntity<Lineup>(lineup, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Lineup>> findAll() {
		List<Lineup> lineup = lineupRepository.findAll();
		return new ResponseEntity<>(lineup, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<Lineup>> delete(long id) {
		lineupRepository.deleteById(id);
		// List<Lineup> lineup = lineupRepository.findAll();
		// return new ResponseEntity<>(lineup, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<Lineup> dirtycheck(long id, String value) {
	// 	Lineup lineup = lineupRepository.findById(id).orElse(null);
	// 	lineup.set("UnitedKingdom");
	//
	// 	Lineup lineup2 = lineupRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(lineup2, HttpStatus.OK);
	// }

}
