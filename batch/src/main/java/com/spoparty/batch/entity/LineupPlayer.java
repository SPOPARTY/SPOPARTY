package com.spoparty.batch.entity;

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
	@Column(name = "lineup_player_id")
	private long id;

	// 등번호
	@Size(min=0, max=3)
	@Column(nullable = true, length=3)
	private String number;

	// 역할
	@Size(min=0, max=20)
	@Column(nullable = true, length=20)
	private String position;

	// 위치 (포메이션 기반)
	@Size(min=0, max=10)
	@Column(nullable=true, length=10)
	private String grid;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lineup_id", nullable=true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Lineup lineup;


	@Size(min=0, max=50)
	@Column(nullable=true, length=50)
	private String name;

	@Builder

	public LineupPlayer(Long id, String number, String position, String grid, Lineup lineup,
		String name) {
		this.id = id;
		this.number = number;
		this.position = position;
		this.grid = grid;
		this.lineup = lineup;
		this.name = name;
	}
}
