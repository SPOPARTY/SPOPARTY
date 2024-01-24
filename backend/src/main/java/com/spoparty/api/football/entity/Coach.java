package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class Coach extends FootballBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
