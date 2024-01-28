package com.spoparty.api.club.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ClubHostRequestDto {
	@NotBlank(message = "currentHostId가 없습니다.")
	private Long currentHostId;
	@NotBlank(message = "nextHostId가 없습니다.")
	private Long nextHostId;
}
