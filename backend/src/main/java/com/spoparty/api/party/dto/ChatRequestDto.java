package com.spoparty.api.party.dto;

import com.spoparty.api.member.entity.Member;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatRequestDto {

	@NotNull(message = "member가 없습니다.")
	Member member;

	@NotNull(message = "message가 없습니다.")
	String message;
}
