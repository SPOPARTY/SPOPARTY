package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.Standings;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class StandingDTO {

	private int rank;

	private int points;

	// 골득실차
	private int goalDiff;

	// 최근 경기 전적
	private String form;

	// 총 경기횟수
	private int played;

	private int win;

	// 무승부
	private int draw;

	private int lose;

	// 득점 수
	private int goalsFor;

	// 실점 수
	private int goalsAgainst;

	private String group;

	public static StandingDTO toDTO(Standings entity) {
		return StandingDTO.builder()
			.rank(entity.getRank())
			.points(entity.getPoints())
			.goalDiff(entity.getGoalDiff())
			.form(entity.getForm())
			.played(entity.getPlayed())
			.win(entity.getWin())
			.draw(entity.getDraw())
			.lose(entity.getLose())
			.goalsFor(entity.getGoalsFor())
			.goalsAgainst(entity.getGoalsAgainst())
			.group(entity.getGroup())
			.build();
	}
}
