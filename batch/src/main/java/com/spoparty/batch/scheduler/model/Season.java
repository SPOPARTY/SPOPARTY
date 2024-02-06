package com.spoparty.batch.scheduler.model;

import lombok.Data;

@Data
public class Season {

	private int year;
	private String start;
	private String end;
	private boolean current;
	private Object coverage;
}
