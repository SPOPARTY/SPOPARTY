package com.spoparty.batch.dummyData.model;

import java.util.List;

import lombok.Data;

@Data
public class StandingLeague {
	Long id;
	String name;
	String country;
	String logo;
	String flag;
	String season;
	List<OuterStanding> standings;
}
