package com.spoparty.batch.dummyData.model;

import java.util.List;
import java.util.Map;

import com.spoparty.batch.scheduler.model.Paging;
import com.spoparty.batch.scheduler.model.Players;

import lombok.Data;

@Data
public class Response<T> {
	private String get;
	private Map<String, String> parameters;
	private List<String> errors;
	private int results;
	private Paging paging;
	private List<T> response;

}