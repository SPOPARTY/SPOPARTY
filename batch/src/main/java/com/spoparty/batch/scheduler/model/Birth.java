package com.spoparty.batch.scheduler.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Birth {

	private String date;
	private String place;
	private String country;

}
