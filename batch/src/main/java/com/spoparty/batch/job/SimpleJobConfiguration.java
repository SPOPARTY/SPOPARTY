package com.spoparty.batch.job;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SimpleJobConfiguration {

	@Bean
	public Job yourJob(JobRepository jobRepository, Step myStep) {
		return new JobBuilder("yourJob", jobRepository)
			.start(myStep)
			// .incrementer(new RunIdIncrementer())
			.build();
	}

	@Bean
	@JobScope
	public Step myStep(JobRepository jobRepository, @Qualifier("metaTxManager") PlatformTransactionManager transactionManager, @Value("#{jobParameters[requestTime]}") String requestDate) {
		return new StepBuilder("myStep", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				log.info(">>>>>>>>>>> requestDate " + requestDate);
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}

	@Bean
	@StepScope
	public Tasklet myTasklet( ) {
		return ((contribution, chunkContext) -> {
			JobParameters jobParameters =  contribution.getStepExecution().getJobExecution().getJobParameters();
			LocalDateTime localDateTime = jobParameters.getLocalDateTime("date");
			log.info(" >>>>>>> jobParameter" + localDateTime.now());
			log.info(">>>> This is Step");
			// log.info(">>>>> jobParmaters " + requestDate);
			return RepeatStatus.FINISHED;
		});
	}



}