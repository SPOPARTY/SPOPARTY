package com.spoparty.batch.scheduler.model;

import java.time.ZonedDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Fixture {
	private int id;
	private String referee;
	private String timezone;
	private String date;
	private long timestamp;
	private Periods periods;
	private Venue venue;
	private Map<String, String> status;

}
