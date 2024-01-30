package com.spoparty.api.club.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InviteRequestDto {
	@NotNull(message = "memberId가 없습니다.")
	private Long memberId;
	@NotNull(message = "초대 URL이 없습니다.")
	private String inviteUrl;
}
