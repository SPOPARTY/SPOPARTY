package com.spoparty.batch.scheduler.model;

import lombok.Data;

@Data
public class Fixtures {
	private Fixture fixture;
	private League league;
	private Teams teams;
	private Goals goals;
	private Score score;

}