package com.spoparty.batch.scheduler.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Fixture {
	private int id;
	private String referee;
	private String timezone;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private ZonedDateTime date;

	private long timestamp;
	private Periods periods;
	private Venue venue;
	private Status status;

}
