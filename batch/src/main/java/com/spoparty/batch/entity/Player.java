package com.spoparty.batch.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player extends FootballBaseEntity {

	@Id
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

	@Builder

	public Player(long id, String nameKr, String nameEng, int age, String nationality, String height, String weight,
		String photo, String position, boolean captain) {
		this.id = id;
		this.nameKr = nameKr;
		this.nameEng = nameEng;
		this.age = age;
		this.nationality = nationality;
		this.height = height;
		this.weight = weight;
		this.photo = photo;
		this.position = position;
		this.captain = captain;
	}


}
