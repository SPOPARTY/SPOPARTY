package com.spoparty.api.football.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class Fixture extends FootballBaseEntity {
	@Id
	@GeneratedValue
	@Column(name = "fixture_id")
	private long id;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;

	@Size(min=0, max=50)
	@Column(nullable = false, length=50)
	private String roundKr;

	@Size(min=0, max=100)
	@Column(nullable = false, length=100)
	private String roundEng;

	@Column(nullable = false, columnDefinition = "TINYINT")
	private int homeTeamGoal;

	@Column(nullable = false, columnDefinition = "TINYINT")
	private int awayTeamGoal;

	@Size(min=0, max=50)
	@Column(nullable = false, length = 50)
	private String status;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "home_team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam homeTeam;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "away_team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam awayTeam;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "season_league_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeague seasonLeauge;

	@OneToMany(mappedBy = "fixture")
	private List<Lineup> lineups = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "fixture")
	private CheerFixture cheerfixture;
}
