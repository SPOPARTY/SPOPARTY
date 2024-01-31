package com.spoparty.api.party.dto;

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
}
