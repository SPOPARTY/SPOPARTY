package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.repository.CoachRepository;
import com.spoparty.api.football.repository.SeasonLeagueRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;
import com.spoparty.api.football.repository.StandingsRepository;
import com.spoparty.api.football.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/seasonLeagueTeam")
@RequiredArgsConstructor
public class SeasonLeagueTeamTestController {

	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	private final CoachRepository coachRepository;
	private final SeasonLeagueRepository seasonLeagueRepository;
	private final TeamRepository teamRepository;
	private final StandingsRepository standingsRepository;

	@GetMapping("/save")
	public ResponseEntity<SeasonLeagueTeam> save() {

		SeasonLeagueTeam seasonLeagueTeam = SeasonLeagueTeam.builder()
			.coach(coachRepository.findById((long)31).orElse(null))
			.seasonLeague(seasonLeagueRepository.findById((long)5).orElse(null))
			.team(teamRepository.findById((long)5).orElse(null))
			.build();

		SeasonLeagueTeam seasonLeagueTeam2 = seasonLeagueTeamRepository.save(seasonLeagueTeam);
		// log.info(seasonLeagueTeam.getId());
		// log.info(seasonLeagueTeam2.getId());

		return new ResponseEntity<SeasonLeagueTeam>(seasonLeagueTeam2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<SeasonLeagueTeam> find(long id) {
		SeasonLeagueTeam seasonLeagueTeam = seasonLeagueTeamRepository.findById(id).orElse(null);

		return new ResponseEntity<SeasonLeagueTeam>(seasonLeagueTeam, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<SeasonLeagueTeam>> findAll() {
		List<SeasonLeagueTeam> seasonLeagueTeam = seasonLeagueTeamRepository.findAll();
		return new ResponseEntity<>(seasonLeagueTeam, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<SeasonLeagueTeam>> delete(long id) {
		seasonLeagueTeamRepository.deleteById(id);
		// List<SeasonLeagueTeam> seasonLeagueTeam = seasonLeagueTeamRepository.findAll();
		// return new ResponseEntity<>(seasonLeagueTeam, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<SeasonLeagueTeam> dirtycheck(long id, String value) {
	// 	SeasonLeagueTeam seasonLeagueTeam = seasonLeagueTeamRepository.findById(id).orElse(null);
	// 	seasonLeagueTeam.set("UnitedKingdom");
	//
	// 	SeasonLeagueTeam seasonLeagueTeam2 = seasonLeagueTeamRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(seasonLeagueTeam2, HttpStatus.OK);
	// }

}
