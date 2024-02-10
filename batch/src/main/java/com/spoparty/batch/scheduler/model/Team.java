package com.spoparty.batch.scheduler.model;

import lombok.Data;

@Data
public class Team {
	private int id;
	private String name;
	private String code;
	private String country;
	private String logo;
	private String winner;
	private int founded;
	private boolean national;
	private Object colors;

}
