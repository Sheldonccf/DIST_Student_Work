package com.example.demo.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobLaunchService {

    private JobLauncher jobLauncher;

    @Autowired
    public JobLaunchService(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public void launchJob(Job job) {
        try {
            JobParameters jobParameters = new JobParameters();
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.getMessage();
            throw new RuntimeException("launch job exception", e);
        }


    }
}
