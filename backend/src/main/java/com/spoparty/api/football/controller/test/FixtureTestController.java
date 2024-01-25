package com.spoparty.api.football.controller.test;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.entity.SeasonLeagueTeamPlayer;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.repository.SeasonLeagueRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/fixture")
@RequiredArgsConstructor
public class FixtureTestController {

	private final FixtureRepository fixtureRepository;
	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	private final SeasonLeagueRepository seasonLeagueRepository;
	@GetMapping("/save")
	public ResponseEntity<Fixture> save() {

		SeasonLeagueTeam homeTeam = seasonLeagueTeamRepository.findById((long)2).orElse(null);
		SeasonLeagueTeam awayTeam = seasonLeagueTeamRepository.findById((long)3).orElse(null);
		SeasonLeague seasonLeague = seasonLeagueRepository.findById((long)5).orElse(null);

		System.out.println(homeTeam.getSeasonLeague().getId());
		System.out.println(awayTeam.getSeasonLeague().getId());
		System.out.println(seasonLeague.getId());

		Fixture fixture = Fixture.builder()
			.awayTeamGoal(1)
			.homeTeamGoal(1)
			.roundKr("라운드 4")
			.roundEng("Roung 4")
			.startTime(LocalDateTime.of(2023, 1, 1, 1, 1))
			.status("종료")
			.homeTeam(homeTeam)
			.awayTeam(awayTeam)
			.seasonLeague(seasonLeague)
			.build();

		Fixture fixture2 = fixtureRepository.save(fixture);
		System.out.println(fixture.getId());
		System.out.println(fixture2.getId());

		return new ResponseEntity<Fixture>(fixture2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<Fixture> find(long id) {
		Fixture fixture = fixtureRepository.findById(id).orElse(null);

		return new ResponseEntity<Fixture>(fixture, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Fixture>> findAll() {
		List<Fixture> fixture = fixtureRepository.findAll();
		return new ResponseEntity<>(fixture, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<Fixture>> delete(long id) {
		fixtureRepository.deleteById(id);
		// List<Fixture> fixture = fixtureRepository.findAll();
		// return new ResponseEntity<>(fixture, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<Fixture> dirtycheck(long id, String value) {
	// 	Fixture fixture = fixtureRepository.findById(id).orElse(null);
	// 	fixture.set("UnitedKingdom");
	//
	// 	Fixture fixture2 = fixtureRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(fixture2, HttpStatus.OK);
	// }

}
