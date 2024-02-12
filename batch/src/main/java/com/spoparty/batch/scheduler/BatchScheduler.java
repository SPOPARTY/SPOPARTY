package com.spoparty.batch.scheduler;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

	private final JobLauncher jobLauncher;
	private final Job job;

	@Scheduled(cron = "0 0 12 * * *")
	public void runJOb() throws Exception {
		JobParameters parameters = new JobParametersBuilder()
			.addString("jobName",  "jpaTest " + LocalDateTime.now())
			.toJobParameters();


		jobLauncher.run(job, parameters);

	}
}
