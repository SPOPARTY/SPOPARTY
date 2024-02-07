package com.spoparty.batch.scheduler.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class LeagueResponse {

	private String get;
	private Map<String, String> parameters;
	private List<String> errors;
	private int results;
	private Paging paging;
	private List<Leagues> response;

}
