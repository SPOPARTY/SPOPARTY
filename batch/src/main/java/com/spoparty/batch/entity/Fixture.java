package com.spoparty.batch.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fixture extends FootballBaseEntity {
	@Id
	@Column(name = "fixture_id")
	private long id;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;

	@Size(min=0, max=50)
	@Column(nullable = true, length=50)
	private String roundKr;

	@Size(min=0, max=100)
	@Column(nullable = true, length=100)
	private String roundEng;

	@Column(nullable = true, columnDefinition = "TINYINT")
	private int homeTeamGoal;

	@Column(nullable = true, columnDefinition = "TINYINT")
	private int awayTeamGoal;

	@Size(min=0, max=50)
	@Column(nullable = true, length = 50)
	private String status;


	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "home_team_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam homeTeam;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "away_team_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam awayTeam;


	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "season_league_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeague seasonLeague;

	@OneToMany(mappedBy = "fixture")
	private List<Lineup> lineups = new ArrayList<>();

	// @OneToOne(fetch = FetchType.LAZY, mappedBy = "fixture")
	// private CheerFixture cheerfixture;


	@Builder
	public Fixture(long id, LocalDateTime startTime, String roundKr, String roundEng, int homeTeamGoal, int awayTeamGoal,
		String status, SeasonLeagueTeam homeTeam, SeasonLeagueTeam awayTeam, SeasonLeague seasonLeague) {
		this.id = id;
		this.startTime = startTime;
		this.roundKr = roundKr;
		this.roundEng = roundEng;
		this.homeTeamGoal = homeTeamGoal;
		this.awayTeamGoal = awayTeamGoal;
		this.status = status;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.seasonLeague = seasonLeague;
	}
}