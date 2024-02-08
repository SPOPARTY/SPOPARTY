package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.Player;
import com.spoparty.api.football.entity.SeasonLeagueTeamPlayer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PlayerDTO {

	private long playerId;

	private String nameKr;

	private String nameEng;

	private int age;

	private String height;

	private String weight;

	private String photo;

	private boolean captain;

	public static PlayerDTO toDTO(SeasonLeagueTeamPlayer entity) {
		Player player = entity.getPlayer();

		return PlayerDTO.builder()
			.playerId(entity.getId())
			.nameKr(player.getNameKr())
			.nameEng(player.getNameEng())
			.age(player.getAge())
			.height(player.getHeight())
			.weight(player.getWeight())
			.photo(player.getPhoto())
			// .captain(entity.isCaptain())
			.build();
	}
}
