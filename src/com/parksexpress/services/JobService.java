package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.Job;

public interface JobService {
	List<Job> getJobListing() throws Exception;
	int getRecordCountForJobs(String jobName);
	List<Job> getJobHistory(String jobName, int rowStart, int offset) throws Exception;
	Job getLastJob(String jobName) throws Exception;
	List<Job> getJobSummary() throws Exception;
	boolean runJob(String jobName) throws Exception;
}
