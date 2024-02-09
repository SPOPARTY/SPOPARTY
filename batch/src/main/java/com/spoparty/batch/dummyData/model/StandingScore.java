package com.spoparty.batch.dummyData.model;

import java.util.Map;

import lombok.Data;

@Data
public class StandingScore {
	int played;
	int win;
	int draw;
	int lose;
	Map<String, Integer> goals;
}
