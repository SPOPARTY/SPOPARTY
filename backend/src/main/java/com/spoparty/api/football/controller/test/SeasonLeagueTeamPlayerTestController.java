package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.SeasonLeagueTeamPlayer;
import com.spoparty.api.football.repository.PlayerRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamPlayerRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/seasonLeagueTeamPlayer")
@RequiredArgsConstructor
public class SeasonLeagueTeamPlayerTestController {

	private final SeasonLeagueTeamPlayerRepository seasonLeagueTeamPlayerRepository;
	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	private final PlayerRepository playerRepository;

	@GetMapping("/save")
	public ResponseEntity<SeasonLeagueTeamPlayer> save() {

		SeasonLeagueTeamPlayer seasonLeagueTeamPlayer = SeasonLeagueTeamPlayer.builder()
			.seasonLeagueTeam(seasonLeagueTeamRepository.findById((long)1).orElse(null))
			.player(playerRepository.findById((long)2).orElse(null))
			.build();

		SeasonLeagueTeamPlayer seasonLeagueTeamPlayer2 = seasonLeagueTeamPlayerRepository.save(seasonLeagueTeamPlayer);
		// log.info(seasonLeagueTeamPlayer.getId());
		// log.info(seasonLeagueTeamPlayer2.getId());

		return new ResponseEntity<SeasonLeagueTeamPlayer>(seasonLeagueTeamPlayer2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<SeasonLeagueTeamPlayer> find(long id) {
		SeasonLeagueTeamPlayer seasonLeagueTeamPlayer = seasonLeagueTeamPlayerRepository.findById(id).orElse(null);

		return new ResponseEntity<SeasonLeagueTeamPlayer>(seasonLeagueTeamPlayer, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<SeasonLeagueTeamPlayer>> findAll() {
		List<SeasonLeagueTeamPlayer> seasonLeagueTeamPlayer = seasonLeagueTeamPlayerRepository.findAll();
		return new ResponseEntity<>(seasonLeagueTeamPlayer, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<SeasonLeagueTeamPlayer>> delete(long id) {
		seasonLeagueTeamPlayerRepository.deleteById(id);
		// List<SeasonLeagueTeamPlayer> seasonLeagueTeamPlayer = seasonLeagueTeamPlayerRepository.findAll();
		// return new ResponseEntity<>(seasonLeagueTeamPlayer, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<SeasonLeagueTeamPlayer> dirtycheck(long id, String value) {
	// 	SeasonLeagueTeamPlayer seasonLeagueTeamPlayer = seasonLeagueTeamPlayerRepository.findById(id).orElse(null);
	// 	seasonLeagueTeamPlayer.set("UnitedKingdom");
	//
	// 	SeasonLeagueTeamPlayer seasonLeagueTeamPlayer2 = seasonLeagueTeamPlayerRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(seasonLeagueTeamPlayer2, HttpStatus.OK);
	// }

}
