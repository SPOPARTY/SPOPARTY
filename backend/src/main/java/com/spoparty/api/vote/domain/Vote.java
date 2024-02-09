package com.spoparty.api.vote.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "vote", timeToLive = 604800) // TTL 1주 604800
public class Vote {
	@Id
	private String voteId;
	@Indexed
	private User user;
	private String title;
	private List<Option> options;
	private Penalty penalty;
	private String answerOptionId;
	@Indexed
	private int ongoing = 1;
	@Indexed
	private String partyId;
	private LocalDateTime createdTime;

	public Vote(User user, String title, List<Option> options, Penalty penalty, String partyId) {
		this.user = user;
		this.title = title;
		this.options = options;
		this.penalty = penalty;
		this.partyId = partyId;
		createdTime = LocalDateTime.now();
	}

	public void finish(String optionId) {
		answerOptionId = optionId;
		ongoing = 0;
	}
}