package com.spoparty.api.party.dto;

import com.spoparty.redis.SubscribeType;

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
public class ChatResponseDto {

	SubscribeType type;

	String sessionId;

	@NotNull(message = "nickName이 없습니다.")
	String userName;

	@NotNull(message = "teamLogo가 없습니다.")
	String teamLogo;

	@NotNull(message = "message가 없습니다.")
	String message;
}
