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
@RedisHash(value = "vote")
public class Vote {
	@Id
	private String id;
	private String content;
	@Indexed
	private Long memberId;
	private String memberNickname;
	private String optionId; // answer
	private String penaltyId;
	@Indexed
	private Long partyId;
	private LocalDateTime createdTime;
}
