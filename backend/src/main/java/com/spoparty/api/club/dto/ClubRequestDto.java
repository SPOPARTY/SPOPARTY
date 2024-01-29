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
public class ClubRequestDto {
	@NotNull(message = "memberId가 없습니다.")
	private Long memberId;
	@NotNull(message = "그룹명이 없습니다.")
	private String name;
}
