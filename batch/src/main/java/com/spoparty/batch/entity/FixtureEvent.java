package com.spoparty.batch.entity;


import java.util.Objects;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixtureEvent extends FootballBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fixture_event_id")
	private long id;

	@Column(nullable=true)
	private long time;

	@Size(min = 0, max = 20)
	@Column(nullable = true, length=20)
	private String type;

	@Size(min = 0, max = 50)
	@Column(nullable = true, length=50)
	private String detail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fixture_id", nullable=true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="season_league_team_id", nullable=true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SeasonLeagueTeam seasonLeagueTeam;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="player_id", nullable=true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FixtureEvent that = (FixtureEvent) o;
		return time == that.time &&
			Objects.equals(type, that.type) &&
			Objects.equals(detail, that.detail) &&
			(fixture == null ? that.fixture == null : Objects.equals(fixture.getId(), that.fixture.getId())) &&
			(seasonLeagueTeam == null ? that.seasonLeagueTeam == null : Objects.equals(seasonLeagueTeam.getId(), that.seasonLeagueTeam.getId())) &&
			(player == null ? that.player == null : Objects.equals(player.getId(), that.player.getId())) &&
			(assist == null ? that.assist == null : Objects.equals(assist.getId(), that.assist.getId()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(time, type, detail,
			(fixture == null ? 0 : fixture.getId()),
			(seasonLeagueTeam == null ? 0 : seasonLeagueTeam.getId()),
			(player == null ? 0 : player.getId()),
			(assist == null ? 0 : assist.getId()));
	}

}
