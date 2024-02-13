package com.spoparty.api.football.entity;

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
public class FixtureEvent extends FootballBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fixture_event_id")
	private long id;

	@Column(nullable = true)
	private long time;

	@Size(min = 0, max = 20)
	@Column(nullable = true, length = 20)
	private String type;

	@Size(min = 0, max = 50)
	@Column(nullable = true, length = 50)
	private String detail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fixture_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "season_league_team_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam seasonLeagueTeam;

	@ManyToOne(fetch = FetchType.LAZY)


	@JoinColumn(name="player_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private LineupPlayer player;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="assist_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private LineupPlayer assist;


	@Builder
	public FixtureEvent(long time, String type, String detail, Fixture fixture, SeasonLeagueTeam seasonLeagueTeam,
		LineupPlayer player, LineupPlayer assist) {
		this.time = time;
		this.type = type;
		this.detail = detail;
		this.fixture = fixture;
		this.seasonLeagueTeam = seasonLeagueTeam;
		this.player = player;
		this.assist = assist;
	}
}
