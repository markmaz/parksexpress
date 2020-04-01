package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.Job;

public interface JobDAO {
	Job getLastJob(String jobName);
	
	List<Job> getJobHistory(String jobName, int rowStart, int offset);
	
	List<Job> getJobSummary();
	
	int getRecordCountForJobHistory(String jobName);
	
	@SuppressWarnings("unchecked")
	List getJobListing();
}
