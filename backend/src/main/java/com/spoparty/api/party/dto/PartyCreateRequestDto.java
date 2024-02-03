package com.spoparty.api.party.dto;

import java.util.HashMap;
import java.util.Map;

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
public class PartyCreateRequestDto {
	@NotNull(message = "clubId가 없습니다.")
	private Long clubId;

	@NotNull(message = "memberId가 없습니다.")
	private Long memberId;

	private Map<String, Object> openViduSessionInfo = new HashMap<>();
}