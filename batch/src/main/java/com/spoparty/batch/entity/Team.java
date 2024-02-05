package com.spoparty.batch.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends FootballBaseEntity {
	@Id
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

	@Builder
	public Team(long id, String nameKr, String nameEng, String logo) {
		this.id = id;
		this.nameKr = nameKr;
		this.nameEng = nameEng;
		this.logo = logo;
	}
}
