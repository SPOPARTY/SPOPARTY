package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class Player extends FootballBaseEntity {

	@Id
	@GeneratedValue
	@Column(name="player_id")
	private long id;

	@Size(min=0, max=100)
	@Column(length=100, nullable=false)
	private String nameKr;

	@Size(min=0, max=200)
	@Column(length=200, nullable=false)
	private String nameEng;

	@Column(nullable=false)
	private int age;

	@Size(min=0, max=100)
	@Column(length=100, nullable=false)
	private String nationality;

	@Size(min=0, max=3)
	@Column(length=3, nullable=false)
	private String height;

	@Size(min=0, max=3)
	@Column(length=3, nullable=false)
	private String weight;

	@Size(min=0, max=200)
	@Column(length=200, nullable=false)
	private String photo;

	@Size(min=0, max=50)
	@Column(length=50, nullable=false)
	private String position;


	@Column(nullable=false, columnDefinition = "TINYINT")
	private boolean captain;

	@OneToMany(mappedBy = "player")
	List<SeasonLeagueTeamPlayer> seasonLeagueTeamPlayers = new ArrayList<>();

}
