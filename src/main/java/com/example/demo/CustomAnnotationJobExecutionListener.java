package com.example.demo;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class CustomAnnotationJobExecutionListener{

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Job is started");
        System.out.println("jobName : "+jobExecution.getJobInstance().getJobName());
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        long  startTime = jobExecution.getStartTime().getTime();
        long  endTime = jobExecution.getEndTime().getTime();
        System.out.println("총 소요시간 : " + (endTime-startTime));

    }
}
