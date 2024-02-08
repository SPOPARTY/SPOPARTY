package com.spoparty.batch.dummyData.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Standing {
	int rank;
	StandingTeam team;
	int points;
	int goalsDiff;
	String group;
	String form;
	String status;
	String description;
	StandingScore all;
	StandingScore home;
	StandingScore away;
	String update;
}
