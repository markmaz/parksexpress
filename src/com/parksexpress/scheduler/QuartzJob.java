package com.parksexpress.scheduler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.parksexpress.services.impl.JobServiceImpl;

public class QuartzJob implements StatefulJob {
	private static Logger log = Logger.getRootLogger();
	
	public QuartzJob(){}
	
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {
		final Map map = context.getJobDetail().getJobDataMap();
		final String service = (String)map.get("service");
		final String server = (String)map.get("server");
		try {
			
			final JobServiceImpl jobService = new JobServiceImpl();
			jobService.setWebServiceURL(server);
			jobService.runJob(service);
		} catch (Exception e) {
			e.printStackTrace();
			QuartzJob.log.fatal("Failed to run service: " + service);
		}
	}
	
}
