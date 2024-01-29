package com.spoparty.api.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineupPlayer extends FootballBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lineup_player_id")
	private long id;

	@Column(nullable = false, columnDefinition = "TINYINT")
	private boolean mainPlayer;

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

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lineup_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Lineup lineup;


	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="season_league_team_player_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeamPlayer seasonLeagueTeamPlayer;




	@Builder

	public LineupPlayer(boolean mainPlayer, String number, String position, String grid, Lineup lineup,
		SeasonLeagueTeamPlayer seasonLeagueTeamPlayer) {
		this.mainPlayer = mainPlayer;
		this.number = number;
		this.position = position;
		this.grid = grid;
		this.lineup = lineup;
		this.seasonLeagueTeamPlayer = seasonLeagueTeamPlayer;
	}
}
