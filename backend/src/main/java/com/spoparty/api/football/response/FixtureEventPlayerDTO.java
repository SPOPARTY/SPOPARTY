package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.Player;

import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@ToString
class FixtureEventPlayerDTO {
	private String nameKr;
	private String nameEng;
	private String photo;

	public static FixtureEventPlayerDTO toDTO(Player entity) {
		return FixtureEventPlayerDTO.builder()
			.nameKr(entity.getNameKr())
			.nameEng(entity.getNameEng())
			.photo(entity.getPhoto())
			.build();
	}
}
