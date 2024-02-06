package com.spoparty.batch.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "season")
public class Season extends FootballBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "season_id")
	private long id;

	@Size(min = 4, max = 4)
	@Column(length = 4, nullable = false)
	private String value;

	@OneToMany(mappedBy = "season", fetch = FetchType.LAZY)
	List<SeasonLeague> seasonLeagues = new ArrayList<>();

	@Builder
	public Season(String value) {
		this.value = value;
	}
}
