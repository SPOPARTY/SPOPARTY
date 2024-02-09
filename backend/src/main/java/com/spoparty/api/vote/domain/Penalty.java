package com.spoparty.api.vote.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@RedisHash(value = "penalty", timeToLive = 604800) // TTL 1주 604800
public class Penalty {
	@Id
	private String penaltyId;
	private String content;

	public Penalty(String content) {
		this.content = content;
	}
}
