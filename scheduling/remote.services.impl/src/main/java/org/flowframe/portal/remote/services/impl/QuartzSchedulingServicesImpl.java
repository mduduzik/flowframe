package org.flowframe.portal.remote.services.impl;

import java.util.Date;

import org.flowframe.scheduling.remote.services.ISchedulingService;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuartzSchedulingServicesImpl implements ISchedulingService {
	
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	
	public void init()
	{
	}

	public SchedulerFactoryBean getSchedulerFactory() {
		return schedulerFactory;
	}

	public void setSchedulerFactory(SchedulerFactoryBean schedulerFactory) {
		this.schedulerFactory = schedulerFactory;
	}

	@Override
	public Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        Scheduler schduler = getSchedulerFactory().getObject();
        schduler.start();
        Date res = schduler.scheduleJob(jobDetail, trigger);
        return res;
	}	
}