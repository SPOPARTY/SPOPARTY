package com.spoparty.api.vote.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoteUpdateRequestDTO {
	@NotNull(message = "memberId가 없습니다.")
	String memberId;
	@NotNull(message = "nickname이 없습니다.")
	String nickname;
	String optionId;
	String answerOptionId;
}