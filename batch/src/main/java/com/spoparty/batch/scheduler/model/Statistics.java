package com.spoparty.batch.scheduler.model;

import lombok.Data;

@Data
public class Statistics {
	private Team team;
	private League league;
	private Games games;
	private Substitutes substitutes;
	private Shots shots;
	private Goals goals;
	private Passes passes;
	private Tackles tackles;
	private Duels duels;
	private Dribbles dribbles;
	private Fouls fouls;
	private Cards cards;
	private Penalty penalty;
}
