package com.spoparty.batch.scheduler.model;

import lombok.Data;

@Data
public class League {
	private int id;
	private String name;
	private Country country;
	private String logo;
	private String flag;
	private String season;
	private String type;
	private String round;

}