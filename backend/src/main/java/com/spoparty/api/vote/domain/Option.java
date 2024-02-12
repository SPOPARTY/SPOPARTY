package com.spoparty.api.vote.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@RedisHash(value = "option", timeToLive = 604800) // TTL 1주 604800
public class Option implements Serializable {
	@Id
	private String optionId;
	private String content;
	private Long count = 0L;
	private List<User> users = new ArrayList<>();

	public Option(String content) {
		this.content = content;
	}

	public void increaseCount() {
		count++;
	}
}