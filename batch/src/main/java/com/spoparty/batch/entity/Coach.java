package com.spoparty.batch.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Coach extends FootballBaseEntity {
	@Id
	@Column(name="coach_id")
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

	@Size(min=0, max=200)
	@Column(length=200, nullable=false)
	private String photo;

	@OneToMany(mappedBy="coach")
	List<SeasonLeagueTeam> seasonLeagueTeams = new ArrayList<>();

	@Builder

	public Coach(long id, String nameKr, String nameEng, int age, String nationality, String photo) {
		this.id = id;
		this.nameKr = nameKr;
		this.nameEng = nameEng;
		this.age = age;
		this.nationality = nationality;
		this.photo = photo;
	}
}
