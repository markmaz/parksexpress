package com.parksexpress.dao.spring.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.dao.JobDAO;
import com.parksexpress.domain.Job;

public class JDBCJobDAO implements JobDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger log = Logger.getRootLogger();

	public JDBCJobDAO() {
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}

	@SuppressWarnings("unchecked")
	public List<Job> getJobHistory(final String jobName, final int rowStart, final int offset) {
		final String sql = "select duration, moment, father_pid, message, message_type, job, description, system_pid, "
				+ "root_pid from job_log jl, jobs j where jl.job=? and jl.job = j.name order by moment DESC LIMIT " + rowStart + ", "
				+ offset;
		this.log.debug(sql);

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { jobName }, new int[] { java.sql.Types.VARCHAR },
				new JobMapper());
	}

	@SuppressWarnings("unchecked")
	public List getJobListing() {
		final String sql = "SELECT distinct name, description FROM jobs";
		this.log.debug(sql);
		return this.simpleJdbcTemplate.getJdbcOperations().queryForList(sql);
	}

	@SuppressWarnings("unchecked")
	public Job getLastJob(final String jobName) {
		final String sql = "select duration, moment, father_pid, message, message_type, job, " + "description, system_pid, root_pid "
				+ "from job_log jl, jobs j " + "where jl.job=? and jl.job = j.name ";
		this.log.debug(sql);

		final List<Job> list = this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { jobName, jobName },
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR }, new JobMapper());

		if (list.size() > 1) {
			for (Job job : list) {
				if (job.getMessageType().equalsIgnoreCase("end")) {
					return job;
				}
			}
		}

		if (list.size() == 1) {
			return list.get(0);
		}

		return null;
	}

	public int getRecordCountForJobHistory(String jobName) {
		return this.simpleJdbcTemplate.queryForInt("SELECT COUNT(*) FROM job_log WHERE job=?", new Object[] { jobName });
	}

	public static final class JobMapper implements RowMapper {
		public JobMapper() {
		}

		public Object mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			final Job job = new Job();
			job.setName(rs.getString("job"));
			job.setDescription(rs.getString("description"));
			job.setFatherPID(rs.getString("father_pid"));
			job.setMessage(rs.getString("message"));
			job.setMessageType(rs.getString("message_type"));
			job.setMoment(rs.getString("moment"));
			job.setRootPID(rs.getString("root_pid"));
			job.setSystemPID(rs.getString("system_pid"));
			job.setDuration(rs.getLong("duration"));
			return job;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Job> getJobSummary() {
		final List<Map<String, Object>> jobs = getJobListing();
		final List<Job> summaryOfJobs = new ArrayList<Job>();

		final String sql = "select distinct jobLogID, duration, moment, father_pid, message, message_type, " +
						   "job, system_pid, root_pid, description from job_log jl, jobs " +
						   "where moment = (Select max(moment) from job_log where job = ?) " +
						   "and jl.job = ? and jobs.name = ?";
		this.log.debug(sql);

		for(Map<String, Object> map : jobs){			
			final List<Job> list = this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[]{(String)map.get("name"),(String)map.get("name"), (String)map.get("name")}, new JobMapper());
			summaryOfJobs.addAll(list);
		}
		
		return summaryOfJobs;
	}

}
