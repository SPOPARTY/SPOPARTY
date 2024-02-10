package com.spoparty.api.party.dto.request;

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

	@Size(max = 30, message = "파티명은 1 ~ 30자 이여야 합니다.")
	private String title;

	private String fixtureUrl;

	private Long fixtureId;
}
