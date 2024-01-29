package com.spoparty.api.club.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ClubRequestDto {
	@NotBlank(message = "memberId가 없습니다.")
	private Long memberId;
	@NotNull(message = "그룹명이 없습니다.")
	private String name;
}