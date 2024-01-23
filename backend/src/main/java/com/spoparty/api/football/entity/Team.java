package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Team extends FootballBaseEntity {
	@Id
	@GeneratedValue
	@Column(name = "team_id")
	private long id;

	@Size(min = 0, max = 100)
	@Column(length = 100, nullable = false)
	private String nameKr;

	@Size(min = 0, max = 200)
	@Column(length = 200, nullable = false)
	private String nameEng;

	@Size(min = 0, max = 200)
	@Column(length = 200, nullable = false)
	private String logo;

	@OneToMany(mappedBy = "team")
	List<SeasonLeagueTeam> seasonLeagueTeams = new ArrayList<>();

}
