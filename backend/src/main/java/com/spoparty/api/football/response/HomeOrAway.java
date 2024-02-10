package com.spoparty.api.football.response;

import org.springframework.data.jpa.repository.Query;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class HomeOrAway {
	private Long homeId;
	private Long awayId;

	@QueryProjection
	public HomeOrAway(Long homeId, Long awayId) {
		this.homeId = homeId;
		this.awayId = awayId;
	}
}
