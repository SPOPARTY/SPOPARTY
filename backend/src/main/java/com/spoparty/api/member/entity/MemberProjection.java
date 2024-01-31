package com.spoparty.api.member.entity;

import java.time.LocalDateTime;

import com.spoparty.api.football.entity.Team;

public interface MemberProjection {

	Long getId();

	String getLoginId();

	String getNickname();

	String getEmail();

	String getProvider();

	String getRoleName();

	Team getTeam();

	int getState();

	LocalDateTime getCreatedTime();

	LocalDateTime getUpdatedTime();

}
