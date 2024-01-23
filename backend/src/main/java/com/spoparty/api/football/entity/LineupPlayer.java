package com.spoparty.api.football.entity;

import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class LineupPlayer extends FootballBaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "lineup_player_id")
	private long id;

	@Column(nullable = false, columnDefinition = "TINYINT")
	private int mainPlayer;

	// 등번호
	@Size(min=0, max=3)
	@Column(nullable = false, length=3)
	private String number;

	// 역할
	@Size(min=0, max=20)
	@Column(nullable = false, length=20)
	private String position;

	// 위치 (포메이션 기반)
	@Size(min=0, max=10)
	@Column(nullable=false, length=10)
	private String grid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lineup_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Lineup lineup;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="season_league_team_player_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeamPlayer seasonLeagueTeamPlayer;




}
