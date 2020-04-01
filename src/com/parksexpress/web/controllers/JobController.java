package com.parksexpress.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.parksexpress.services.JobService;

public class JobController extends MultiActionController {
	private JobService jobService;
	private Logger log = Logger.getRootLogger();
	
	public JobController(){}
	
	public void setJobService(final JobService jobService) {
		this.jobService = jobService;
	}

	public ModelAndView getSummary(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		this.log.debug("Get Summary called.");
		final ModelAndView view = new ModelAndView("jobSummary");
		view.addObject("jobs", this.jobService.getJobSummary());
		return view;
	}
	
	public ModelAndView runJob(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		this.log.debug("Running job - " + request.getParameter("jobName"));
		this.jobService.runJob(request.getParameter("jobName"));
		final ModelAndView view = new ModelAndView("jobSummary");
		view.addObject("jobs", this.jobService.getJobSummary());
		return view;
	}
	
	public ModelAndView showHistory(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String jobName = request.getParameter("jobName");
		final String rowStart = request.getParameter("rowStart");
		final String offset = request.getParameter("offset");
		this.log.debug("Parameters for showHistory: " + jobName + ":" + rowStart + ":" + offset);
		
		int row = 0;
		int off = 20;
		
		if(!StringUtils.isEmpty(rowStart)){
			row = new Integer(rowStart).intValue();
		}
		
		if(!StringUtils.isEmpty(offset)){
			off = new Integer(offset).intValue();
		}
		
		final int records = this.jobService.getRecordCountForJobs(jobName);
		final float pages = records/off;
		
		final int actualPages = Math.round(pages);
		
		final ModelAndView view = new ModelAndView("jobHistory");
		view.addObject("jobs", this.jobService.getJobHistory(jobName, row, off));
		view.addObject("jobName", jobName);
		view.addObject("pages", new Integer(actualPages).toString());
		return view;
	}
}