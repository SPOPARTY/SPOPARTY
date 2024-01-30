package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.CheerFixture;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class CheerFixtureDTO {
	private long cheerFixtureId;
	private boolean alreadyCheer = false;
	private long homeCount;
	private long awayCount;
	private FixtureDTO fixture;

	public void switchAlreadyCheer(){
		alreadyCheer = true;
	}
	public static CheerFixtureDTO toDTO(CheerFixture entity) {

		FixtureDTO fixtureDTO = FixtureDTO.toDTO(entity.getFixture());

		return CheerFixtureDTO.builder()
			.cheerFixtureId(entity.getId())
			.homeCount(entity.getHomeCount())
			.awayCount(entity.getAwayCount())
			.fixture(fixtureDTO)
			.build();
	}

}
