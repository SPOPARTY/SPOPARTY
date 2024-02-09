package com.spoparty.api.vote.domain;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@RedisHash(value = "user", timeToLive = 604800) // TTL 1주 604800
public class User {
	@Indexed
	private String userId;
	private String name;
}
