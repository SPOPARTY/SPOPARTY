package com.spoparty.api.vote.dto;

import java.io.Serializable;

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
public class VoteCountingRequestDTO implements Serializable {
	@NotNull(message = "partyId가 없습니다.")
	String partyId;
	@NotNull(message = "voteId가 없습니다.")
	String voteId;
	@NotNull(message = "answerOptionId가 없습니다.")
	String answerOptionId;
}