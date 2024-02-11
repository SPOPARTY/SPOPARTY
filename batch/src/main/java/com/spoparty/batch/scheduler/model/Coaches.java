package com.spoparty.batch.scheduler.model;

import java.util.List;

import lombok.Data;

@Data
public class Coaches {
	private int id;
	private String name;
	private String firstname;
	private String lastname;
	private int age;
	private Birth birth;
	private String nationality;
	private String height;
	private String weight;
	private String photo;
	private Team team;
	private List<Career> career;
}
