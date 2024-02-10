package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class League extends FootballBaseEntity {

	@Id
	@Column(name = "league_id")
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

	@Size(min = 0, max = 100)
	@Column(length = 100, nullable = false)
	private String country;

	@Size(min = 0, max = 200)
	@Column(length = 200, nullable = false)
	private String countryLogo;

	@Column(length = 10, nullable = false)
	private String type;

	@OneToMany(mappedBy = "league")
	List<SeasonLeague> seasonLeagues = new ArrayList<>();

	public void setCountry(String country) {
		this.country = country;
	}

	@Builder
	public League(long id, String nameKr, String nameEng, String logo, String country, String countryLogo,
		String type) {
		this.id = id;
		this.nameKr = nameKr;
		this.nameEng = nameEng;
		this.logo = logo;
		this.country = country;
		this.countryLogo = countryLogo;
		this.type = type;
	}

}
