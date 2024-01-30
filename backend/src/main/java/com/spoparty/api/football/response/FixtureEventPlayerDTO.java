package com.spoparty.api.football.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FixtureEventPlayerDTO {
	private String nameKr;
	private String nameEng;
	private String photo;

	@QueryProjection
	public FixtureEventPlayerDTO(String nameKr, String nameEng, String photo) {
		this.nameKr = nameKr;
		this.nameEng = nameEng;
		this.photo = photo;
	}
	// public static FixtureEventPlayerDTO toDTO(Player entity) {
	// 	return FixtureEventPlayerDTO.builder()
	// 		.nameKr(entity.getNameKr())
	// 		.nameEng(entity.getNameEng())
	// 		.photo(entity.getPhoto())
	// 		.build();
	// }
}
