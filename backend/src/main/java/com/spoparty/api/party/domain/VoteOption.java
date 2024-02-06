package com.spoparty.api.party.domain;

import java.time.LocalDateTime;

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
@RedisHash(value = "voteOption")
public class VoteOption {
	@Id
	private String id;
	@Indexed
	private String voteId;
	private String optionId;
	private Long memberId;
	private String memberNickname;
	@Indexed
	private LocalDateTime createdTime;
}
