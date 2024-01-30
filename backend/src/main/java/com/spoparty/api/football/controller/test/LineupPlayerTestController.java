package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.LineupPlayer;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.repository.LineupPlayerRepository;
import com.spoparty.api.football.repository.LineupRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamPlayerRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/football/test/lineupPlayer")
@RequiredArgsConstructor
@Slf4j
public class LineupPlayerTestController {

	private final LineupPlayerRepository lineupPlayerRepository;
	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	private final FixtureRepository fixtureRepository;
	private final SeasonLeagueTeamPlayerRepository seasonLeagueTeamPlayerRepository;
	private final LineupRepository lineupRepository;

	@GetMapping("/save")
	public ResponseEntity<LineupPlayer> save() {



		LineupPlayer lineupPlayer = LineupPlayer.builder()
			.lineup(lineupRepository.findById((long)1).orElse(null))
			.grid("1:3")
			.number("3")
			.mainPlayer(true)
			.position("공격수")
			.seasonLeagueTeamPlayer(seasonLeagueTeamPlayerRepository.findById((long)1).orElse(null))
			.build();

		LineupPlayer lineupPlayer2 = lineupPlayerRepository.save(lineupPlayer);
		// log.info(lineupPlayer.getId());
		// log.info(lineupPlayer2.getId());

		return new ResponseEntity<LineupPlayer>(lineupPlayer2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<LineupPlayer> find(long id) {
		LineupPlayer lineupPlayer = lineupPlayerRepository.findById(id).orElse(null);

		return new ResponseEntity<LineupPlayer>(lineupPlayer, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<LineupPlayer>> findAll() {
		List<LineupPlayer> lineupPlayer = lineupPlayerRepository.findAll();
		return new ResponseEntity<>(lineupPlayer, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<LineupPlayer>> delete(long id) {
		lineupPlayerRepository.deleteById(id);
		// List<LineupPlayer> lineupPlayer = lineupPlayerRepository.findAll();
		// return new ResponseEntity<>(lineupPlayer, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<LineupPlayer> dirtycheck(long id, String value) {
	// 	LineupPlayer lineupPlayer = lineupPlayerRepository.findById(id).orElse(null);
	// 	lineupPlayer.set("UnitedKingdom");
	//
	// 	LineupPlayer lineupPlayer2 = lineupPlayerRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(lineupPlayer2, HttpStatus.OK);
	// }

}
