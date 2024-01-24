package com.spoparty.api.football.controller.test;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.repository.LeagueRepository;
import com.spoparty.api.football.repository.SeasonLeagueRepository;
import com.spoparty.api.football.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/seasonLeague")
@RequiredArgsConstructor
public class SeasonLeagueTestController {

	private final SeasonLeagueRepository seasonLeagueRepository;
	private final SeasonRepository seasonRepository;
	private final LeagueRepository leagueRepository;
	@GetMapping("/save")
	public ResponseEntity<SeasonLeague> save() {

		SeasonLeague seasonLeague = SeasonLeague.builder()
			.seasonStartDate(LocalDateTime.of(2023, 7, 10, 0, 0 ))
			.seasonEndDate(LocalDateTime.of(2024, 3, 2, 0, 0))
			.season(seasonRepository.findById((long)3).orElse(null))
			.league(leagueRepository.findById((long)47).orElse(null))
			.build();

		SeasonLeague seasonLeague2 = seasonLeagueRepository.save(seasonLeague);
		System.out.println(seasonLeague.getId());
		System.out.println(seasonLeague2.getId());

		return new ResponseEntity<SeasonLeague>(seasonLeague2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<SeasonLeague> find(long id) {
		SeasonLeague seasonLeague = seasonLeagueRepository.findById(id).orElse(null);

		return new ResponseEntity<SeasonLeague>(seasonLeague, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<SeasonLeague>> findAll() {
		List<SeasonLeague> seasonLeague = seasonLeagueRepository.findAll();
		return new ResponseEntity<>(seasonLeague, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<SeasonLeague>> delete(long id) {
		seasonLeagueRepository.deleteById(id);
		// List<SeasonLeague> seasonLeague = seasonLeagueRepository.findAll();
		// return new ResponseEntity<>(seasonLeague, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<SeasonLeague> dirtycheck(long id, String value) {
	// 	SeasonLeague seasonLeague = seasonLeagueRepository.findById(id).orElse(null);
	// 	seasonLeague.set("UnitedKingdom");
	//
	// 	SeasonLeague seasonLeague2 = seasonLeagueRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(seasonLeague2, HttpStatus.OK);
	// }

}
