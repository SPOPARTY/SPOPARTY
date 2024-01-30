package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Cheer;
import com.spoparty.api.football.repository.CheerFixtureRepository;
import com.spoparty.api.football.repository.CheerRepository;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamPlayerRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;
import com.spoparty.api.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/football/test/cheer")
@RequiredArgsConstructor
@Slf4j
public class CheerTestController {

	private final CheerRepository cheerRepository;
	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	private final CheerFixtureRepository cheerFixtureRepository;
	private final MemberRepository memberRepository;
	@GetMapping("/save")
	public ResponseEntity<Cheer> save() {





		Cheer cheer = Cheer.builder()
			.seasonLeagueTeam(seasonLeagueTeamRepository.findById((long)2).orElse(null))
			.cheerFixture(cheerFixtureRepository.findById((long)1).orElse(null))
			.member(memberRepository.findById((long)1).orElse(null))
			.build();

		Cheer cheer2 = cheerRepository.save(cheer);
		// log.info(cheer.getId());
		// log.info(cheer2.getId());

		return new ResponseEntity<Cheer>(cheer2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<Cheer> find(long id) {
		Cheer cheer = cheerRepository.findById(id).orElse(null);

		return new ResponseEntity<Cheer>(cheer, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Cheer>> findAll() {
		List<Cheer> cheer = cheerRepository.findAll();
		return new ResponseEntity<>(cheer, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<Cheer>> delete(long id) {
		cheerRepository.deleteById(id);
		// List<Cheer> cheer = cheerRepository.findAll();
		// return new ResponseEntity<>(cheer, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<Cheer> dirtycheck(long id, String value) {
	// 	Cheer cheer = cheerRepository.findById(id).orElse(null);
	// 	cheer.set("UnitedKingdom");
	//
	// 	Cheer cheer2 = cheerRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(cheer2, HttpStatus.OK);
	// }

}
