package com.spoparty.api.football.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FixtureEventTeamDTO {
	private String nameKr;
	private String nameEng;
	private String logo;

	@QueryProjection
	public FixtureEventTeamDTO(String nameKr, String nameEng, String logo) {
		this.nameKr = nameKr;
		this.nameEng = nameEng;
		this.logo = logo;
	}

	// public static FixtureEventTeamDTO toDTO(Team entity){
	// 	return FixtureEventTeamDTO.builder()
	// 		.nameKr(entity.getNameKr())
	// 		.nameEng(entity.getNameEng())
	// 		.logo(entity.getLogo())
	// 		.build();
	// }
}
