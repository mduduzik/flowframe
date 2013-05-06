package org.flowframe.scheduling.remote.services;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;


public interface ISchedulingService {
    public Date scheduleJob(JobDetail jobDetail, Trigger trigger)
            throws SchedulerException;
}
