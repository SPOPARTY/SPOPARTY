package com.spoparty.api.party.dto.request;

import com.spoparty.redis.SubscribeType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatRequestDto {

	SubscribeType type = SubscribeType.BROAD_CAST;

	@NotNull(message = "userName이 없습니다.")
	String userName;

	@NotNull(message = "clubId가 없습니다.")
	String clubId;

	@NotNull(message = "partyId가 없습니다.")
	String partyId;

	@NotNull(message = "teamLogo가 없습니다.")
	String teamLogo;

	@NotNull(message = "message가 없습니다.")
	String message;
}
