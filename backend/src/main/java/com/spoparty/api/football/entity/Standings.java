package com.spoparty.api.football.entity;

import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class Standings extends FootballBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "standings_id")
	private long id;

	@Column(nullable=false)
	private int rank;

	@Column(nullable = false)
	private int points;

	// 골득실차
	@Column(nullable=false)
	private int goalDiff;

	// 최근 경기 전적
	@Size(min=0, max=5)
	@Column(nullable=false, length = 5)
	private String form;

	// 총 경기횟수
	@Column(nullable=false)
	private int played;

	@Column(nullable=false)
	private int win;

	// 무승부
	@Column(nullable=false)
	private int draw;

	@Column(nullable=false)
	private int lose;

	// 득점 수
	@Column(nullable=false)
	private int goalsFor;

	// 실점 수
	@Column(nullable=false)
	private int goalsAgainst;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "standing")
	private SeasonLeagueTeam seasonLeagueTeam;
}
