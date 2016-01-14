package com.testmyinterview.portal.kookoo;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ScheduledJob extends QuartzJobBean {

	private ScheduleCallBean scheduleCallBean;

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		scheduleCallBean.getInterviewDetails();
	}

	/**
	 * This Method is used to set schedule call bean
	 * @param scheduleCallBean
	 */
	public void setScheduleCallBean(ScheduleCallBean scheduleCallBean) {
		this.scheduleCallBean = scheduleCallBean;
	}
}