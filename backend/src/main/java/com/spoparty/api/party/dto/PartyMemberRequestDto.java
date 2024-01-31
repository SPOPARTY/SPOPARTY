package com.spoparty.api.party.dto;

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
public class PartyMemberRequestDto {
	@NotNull(message = "memberId가 없습니다.")
	private Long memberId;

	// @NotNull(message = "openViduSessionId가 없습니다.")
	// private String openViduSessionId;

	@NotNull(message = "openViduConnectionInfo가 없습니다.")
	private Map<String, Object> openViduConnectionInfo;
}
