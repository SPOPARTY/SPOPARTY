package com.spoparty.batch.scheduler.model;

import lombok.Data;

@Data
public class League {
	private int id;
	private String name;
	private String country;
	private String logo;
	private String flag;
	private int season;
	private String round;

}