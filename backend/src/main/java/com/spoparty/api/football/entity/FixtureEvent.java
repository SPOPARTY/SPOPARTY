package com.spoparty.api.football.entity;

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
import lombok.Getter;

@Entity
@Getter
public class FixtureEvent extends FootballBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fixture_event_id")
	private long id;

	@Column(nullable=false)
	private long time;

	@Size(min = 0, max = 20)
	@Column(nullable = false, length=20)
	private String type;

	@Size(min = 0, max = 50)
	@Column(nullable = false, length=50)
	private String detail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fixture_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="season_team_team_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam seasonLeagueTeam;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="player_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeamPlayer player;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="assist_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeamPlayer assist;

}
