package com.spoparty.api.club.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubHostRequestDto {
	@NotBlank(message = "currentHostId가 없습니다.")
	private long currentHostId;
	@NotBlank(message = "nextHostId가 없습니다.")
	private long nextHostId;
}
