package com.spoparty.api.football.response;

import com.spoparty.api.football.entity.Coach;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CoachDTO {
	private long coachId;

	private String nameKr;

	private String nameEng;

	private String photo;

	private String nationality;

	public static CoachDTO toDTO(Coach entity){
		return CoachDTO.builder()
			.coachId(entity.getId())
			.nameKr(entity.getNameKr())
			.nameEng(entity.getNameEng())
			.photo(entity.getPhoto())
			.nationality(entity.getNationality())
			.build();
	}
}
