package com.spoparty.api.club.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClubHostRequestDto {
	@NotNull(message = "currentHostId가 없습니다.")
	private Long currentHostId;
	@NotNull(message = "nextHostId가 없습니다.")
	private Long nextHostId;
}
