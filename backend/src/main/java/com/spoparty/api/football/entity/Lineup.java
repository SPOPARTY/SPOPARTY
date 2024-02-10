package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lineup extends FootballBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lineup_id")
	private long id;

	@Size(min = 0, max = 20)
	@Column(nullable = true, length = 20)
	private String formation;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fixture_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "season_league_team_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam seasonLeagueTeam;

	@OneToMany(mappedBy = "lineup")
	private List<LineupPlayer> lineupPlayers = new ArrayList<>();

	@Builder

	public Lineup(String formation, Fixture fixture, SeasonLeagueTeam seasonLeagueTeam) {
		this.formation = formation;
		this.fixture = fixture;
		this.seasonLeagueTeam = seasonLeagueTeam;
	}
}
