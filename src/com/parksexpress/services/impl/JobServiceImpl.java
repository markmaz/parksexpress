package com.parksexpress.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.parksexpress.dao.JobDAO;
import com.parksexpress.domain.Job;
import com.parksexpress.services.JobService;

public class JobServiceImpl implements JobService {
	private JobDAO jobDAO;
	private String webServiceURL;
	
	public JobServiceImpl(){}
	
	public void setJobDAO(final JobDAO jobDAO) {
		this.jobDAO = jobDAO;
	}

	public void setWebServiceURL(String url){
		this.webServiceURL = url;
	}
	
	public List<Job> getJobHistory(final String jobName, final int rowStart, final int offset) throws Exception {
		if (StringUtils.isEmpty(jobName)) {
			throw new Exception("Parameter can not be null or blank.");
		}

		return this.jobDAO.getJobHistory(jobName, rowStart, offset);
	}

	@SuppressWarnings("unchecked")
	public List getJobListing() throws Exception {
		return this.jobDAO.getJobListing();
	}

	public Job getLastJob(final String jobName) throws Exception {
		return this.jobDAO.getLastJob(jobName);
	}

	public boolean runJob(final String jobName) throws Exception {
		final URL urlEndPoint = new URL(this.webServiceURL + jobName
				+ "/services/" + jobName + "?method=runJob");
		final URLConnection conn = urlEndPoint.openConnection();
		conn.setDoOutput(true);
		conn.setAllowUserInteraction(false);

		final BufferedReader br = new BufferedReader(new InputStreamReader(conn
				.getInputStream()));
		String str;
		final StringBuffer sb = new StringBuffer();
		
		while ((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();
		
		@SuppressWarnings("unused")
		final String response = sb.toString();

		return true;
	}

	public List<Job> getJobSummary() throws Exception {
		return this.jobDAO.getJobSummary();
	}

	public int getRecordCountForJobs(String jobName) {
		return this.jobDAO.getRecordCountForJobHistory(jobName);
	}

}
