package com.spoparty.api.football.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmblemDTO {
	private long teamId;
	private String name;
	private String logo;
	private Boolean isMain = false;

	public void switchIsMain(){
		isMain = true;
	}
	@QueryProjection
	public EmblemDTO(long teamId, String name, String logo, Boolean isMain) {
		this.teamId = teamId;
		this.name = name;
		this.logo = logo;
		this.isMain = isMain;

	}
}
