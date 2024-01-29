package com.spoparty.api.football.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.Length;

import com.spoparty.api.common.entity.FootballBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
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

	public void setValue(String value) {
		this.value = value;
	}
}
