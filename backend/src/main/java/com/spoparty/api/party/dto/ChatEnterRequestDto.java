package com.spoparty.api.party.dto;

import java.io.Serializable;

import org.springframework.data.redis.connection.Message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spoparty.api.member.entity.Member;
import com.spoparty.redis.SubscribeType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import okhttp3.internal.ws.RealWebSocket;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatEnterRequestDto {

	SubscribeType type;

	@NotNull(message = "nickName이 없습니다.")
	String userName;

	@NotNull(message = "teamLogo가 없습니다.")
	String teamLogo;

	@NotNull(message = "teamId가 없습니다.")
	String teamId;

	@NotNull(message = "groupId가 없습니다.")
	String groupId;

	String sessionId;
}
