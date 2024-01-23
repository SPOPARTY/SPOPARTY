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
import lombok.Getter;

@Entity
@Getter
public class SeasonLeagueTeamPlayer extends FootballBaseEntity {
	@Id
	@GeneratedValue
	@Column(name = "season_league_team_player_id")
	private long id;

	@Column(nullable = false, columnDefinition = "TINYINT")
	private boolean captain;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "season_league_team_id", nullable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam seasonLeagueTeam;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Player player;

	@OneToMany(mappedBy = "seasonLeagueTeamPlayer")
	private List<LineupPlayer> lineupPlayers = new ArrayList<>();
}
