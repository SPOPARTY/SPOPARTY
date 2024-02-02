package com.spoparty.api.party.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class PartyUpdateRequestDto {
	@NotNull(message = "memberId가 없습니다.")
	private Long memberId;

	// @NotNull(message = "파티명이 없습니다.")
	@Size(max = 30, message = "파티명은 1 ~ 30자 이여야 합니다.")
	private String title;

	// @NotNull(message = "경기 URL이 없습니다.")
	private String fixtureUrl;

	// @NotNull(message = "fixtureId가 없습니다.")
	private Long fixtureId;
}
