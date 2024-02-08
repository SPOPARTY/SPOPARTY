package com.spoparty.batch.scheduler.model;

import lombok.Data;

@Data
public class Venue {
	private String id;
	private String name;
	private String city;
	private String address;
	private int capacity;
	private String surface;
	private String image;
}
