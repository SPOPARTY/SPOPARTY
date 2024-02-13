package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.FixtureEvent;
import com.spoparty.api.football.repository.FixtureEventRepository;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamPlayerRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/football/test/fixtureEvent")
@RequiredArgsConstructor
@Slf4j
public class FixtureEventTestController {


	//
	// private final FixtureEventRepository fixtureEventRepository;
	// private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	// private final FixtureRepository fixtureRepository;
	// private final SeasonLeagueTeamPlayerRepository seasonLeagueTeamPlayerRepository;
	// @GetMapping("/save")
	// public ResponseEntity<FixtureEvent> save() {
	//
	//
	//
	//
	//
	// 	FixtureEvent fixtureEvent = FixtureEvent.builder()
	// 		.fixture(fixtureRepository.findById((long)0).orElse(null))
	// 		.type("Goal")
	// 		.detail("Normal Goal")
	// 		.time((long)23)
	// 		.seasonLeagueTeam(seasonLeagueTeamRepository.findById((long)2).orElse(null))
	// 		.player(seasonLeagueTeamPlayerRepository.findById((long)1).orElse(null))
	// 		.assist(null)
	// 		.build();
	//
	// 	FixtureEvent fixtureEvent2 = fixtureEventRepository.save(fixtureEvent);
	// 	// log.info(fixtureEvent.getId());
	// 	// log.info(fixtureEvent2.getId());
	//
	// 	return new ResponseEntity<FixtureEvent>(fixtureEvent2, HttpStatusCode.valueOf(200));
	// }
	//
	// @GetMapping("/find")
	// public ResponseEntity<FixtureEvent> find(long id) {
	// 	FixtureEvent fixtureEvent = fixtureEventRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<FixtureEvent>(fixtureEvent, HttpStatusCode.valueOf(200));
	//
	// }
	//
	// @GetMapping("/findAll")
	// public ResponseEntity<List<FixtureEvent>> findAll() {
	// 	List<FixtureEvent> fixtureEvent = fixtureEventRepository.findAll();
	// 	return new ResponseEntity<>(fixtureEvent, HttpStatus.OK);
	// }
	//
	// @GetMapping("/delete")
	// public ResponseEntity<List<FixtureEvent>> delete(long id) {
	// 	fixtureEventRepository.deleteById(id);
	// 	// List<FixtureEvent> fixtureEvent = fixtureEventRepository.findAll();
	// 	// return new ResponseEntity<>(fixtureEvent, HttpStatus.OK);
	// 	return null;
	// }
	//
	// // @Transactional
	// // @GetMapping("/dirtycheck-test")
	// // public ResponseEntity<FixtureEvent> dirtycheck(long id, String value) {
	// // 	FixtureEvent fixtureEvent = fixtureEventRepository.findById(id).orElse(null);
	// // 	fixtureEvent.set("UnitedKingdom");
	// //
	// // 	FixtureEvent fixtureEvent2 = fixtureEventRepository.findById(id).orElse(null);
	// //
	// // 	return new ResponseEntity<>(fixtureEvent2, HttpStatus.OK);
	// // }

}
