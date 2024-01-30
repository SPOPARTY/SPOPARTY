package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Team;
import com.spoparty.api.football.repository.TeamRepository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/team")
@RequiredArgsConstructor
public class TeamTestController {

	private final TeamRepository teamRepository;
	@GetMapping("/save")
	public ResponseEntity<Team> save() {

		Team team = Team.builder()
			.id(5)
			.nameKr("무무무")
			.nameEng("lllll")
			.logo("https://source.unsplash.com/random/300x300?emblem")
			.build();

		Team team2 = teamRepository.save(team);
		// log.info(team.getId());
		// log.info(team2.getId());

		return new ResponseEntity<Team>(team2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<Team> find(long id) {
		Team team = teamRepository.findById(id).orElse(null);

		return new ResponseEntity<Team>(team, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Team>> findAll() {
		List<Team> team = teamRepository.findAll();
		return new ResponseEntity<>(team, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<Team>> delete(long id) {
		teamRepository.deleteById(id);
		// List<Team> team = teamRepository.findAll();
		// return new ResponseEntity<>(team, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<Team> dirtycheck(long id, String value) {
	// 	Team team = teamRepository.findById(id).orElse(null);
	// 	team.set("UnitedKingdom");
	//
	// 	Team team2 = teamRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(team2, HttpStatus.OK);
	// }

}
