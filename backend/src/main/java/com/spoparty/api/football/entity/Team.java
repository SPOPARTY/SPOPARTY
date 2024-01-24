package com.spoparty.api.football.entity;

import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Team extends FootballBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
