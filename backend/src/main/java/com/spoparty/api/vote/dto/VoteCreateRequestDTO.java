package com.spoparty.api.vote.dto;

import java.util.List;

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
public class VoteCreateRequestDTO {
	@NotNull(message = "memberId가 없습니다.")
	String memberId;
	@NotNull(message = "nickname이 없습니다.")
	String nickname;
	@NotNull(message = "title이 없습니다.")
	String title;
	@NotNull(message = "options가 없습니다.")
	List<String> options;
	@NotNull(message = "penalty가 없습니다.")
	String penalty;
}