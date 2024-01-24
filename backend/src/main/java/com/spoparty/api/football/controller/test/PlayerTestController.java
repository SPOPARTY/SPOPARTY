package com.spoparty.api.football.controller.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Player;
import com.spoparty.api.football.repository.PlayerRepository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/football/test/player")
@RequiredArgsConstructor
public class PlayerTestController {

	private final PlayerRepository playerRepository;
	@GetMapping("/save")
	public ResponseEntity<Player> save() {

		Player player = Player.builder()
			.id(3)
			.age(22)
			.captain(false)
			.height("178")
			.photo("https://source.unsplash.com/random/300x300?person")
			.nationality("England")
			.nameKr("이효성")
			.nameEng("Yohan Kim")
			.position("수비수")
			.weight("85")
			.build();

		Player player3 = Player.builder()
			.id(2)
			.age(20)
			.height("183")
			.photo("https://source.unsplash.com/random/300x300?person")
			.nationality("Korea")
			.nameKr("이교환")
			.nameEng("llll")
			.position("골키퍼")
			.weight("80")
			.captain(false)
			.build();
		Player player2 = playerRepository.save(player);
		playerRepository.save(player3);
		System.out.println(player.getId());
		System.out.println(player2.getId());

		return new ResponseEntity<Player>(player2, HttpStatusCode.valueOf(200));
	}

	@GetMapping("/find")
	public ResponseEntity<Player> find(long id) {
		Player player = playerRepository.findById(id).orElse(null);

		return new ResponseEntity<Player>(player, HttpStatusCode.valueOf(200));

	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Player>> findAll() {
		List<Player> player = playerRepository.findAll();
		return new ResponseEntity<>(player, HttpStatus.OK);
	}

	@GetMapping("/delete")
	public ResponseEntity<List<Player>> delete(long id) {
		playerRepository.deleteById(id);
		// List<Player> player = playerRepository.findAll();
		// return new ResponseEntity<>(player, HttpStatus.OK);
		return null;
	}

	// @Transactional
	// @GetMapping("/dirtycheck-test")
	// public ResponseEntity<Player> dirtycheck(long id, String value) {
	// 	Player player = playerRepository.findById(id).orElse(null);
	// 	player.set("UnitedKingdom");
	//
	// 	Player player2 = playerRepository.findById(id).orElse(null);
	//
	// 	return new ResponseEntity<>(player2, HttpStatus.OK);
	// }

}
