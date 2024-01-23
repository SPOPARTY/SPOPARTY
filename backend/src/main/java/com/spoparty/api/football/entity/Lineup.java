package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class Lineup extends FootballBaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "lineup_id")
	private long id;

	@Size(min=0, max=20)
	@Column(nullable=false, length=20)
	private String formation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fixture_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "season_league_team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam seasonLeagueTeam;

	@OneToMany(mappedBy = "lineup")
	private List<LineupPlayer> lineupPlayers = new ArrayList<>();

}
