package com.spoparty.batch.scheduler.model;

import lombok.Data;

@Data
public class Penalty {
	private Integer won;
	private Integer commited;
	private Integer scored;
	private Integer missed;
	private Integer saved;

}
