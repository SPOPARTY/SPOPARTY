package com.spoparty.api.vote.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.spoparty.api.vote.domain.Option;
import com.spoparty.api.vote.domain.Penalty;
import com.spoparty.api.vote.domain.User;
import com.spoparty.api.vote.domain.Vote;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VoteCountingResponseDTO {
	String voteId;
	User user;
	String title;
	List<Option> options;
	Penalty penalty;
	String answerOptionId;
	String answerOptionContent;
	int ongoing;
	String partyId;
	int voteCount = 0;
	List<User> penaltyUsers;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	LocalDateTime createdTime;

	public VoteCountingResponseDTO(Vote vote, List<User> penaltyUsers, String answerOptionContent) {
		voteId = vote.getVoteId();
		user = vote.getUser();
		title = vote.getTitle();
		options = vote.getOptions();
		penalty = vote.getPenalty();
		answerOptionId = vote.getAnswerOptionId();
		ongoing = vote.getOngoing();
		partyId = vote.getPartyId();
		voteCount = vote.getVoteCount();
		createdTime = vote.getCreatedTime();
		this.penaltyUsers = penaltyUsers;
		this.answerOptionContent = answerOptionContent;
	}
}