package org.flowframe.scheduling.remote.services.impl.tests;


import javax.transaction.UserTransaction;

import org.flowframe.portal.remote.services.impl.QuartzSchedulingServicesImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/flowframe/tm.jta-module-context.xml",
        "/META-INF/flowframe/jpa.lob.jpacontainer.springdm-module-context.xml",
        "/META-INF/flowframe/tm.jta-module-context.xml",
        "/META-INF/core.dao.datasource.mysql-module-context.xml",
        "/META-INF/scheduling-module-context.xml"})
public class QuartzSchedulingServicesImplTests  {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private ApplicationContext applicationContext;
	
	private UserTransaction userTransactionManager;

	@Autowired
	private JobsManagerImpl quartzSchdulingService;
	
	
	@Before
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(quartzSchdulingService);
        
        quartzSchdulingService.init();
        
        
        userTransactionManager = (UserTransaction) applicationContext.getBean("globalBitronixTransactionManager");
        Assert.assertNotNull(userTransactionManager);
    }	
	
	@After
	public void tearDown() throws Exception {
	}

    @Test
    public void testSimpleCron() throws Exception {
        log.info("------- Scheduling Job  -------------------");

        // define the job and tie it to our HelloJob class
        JobDetail job = JobBuilder.newJob(HelloJob.class)
            .withIdentity("job2", "group1")
            .build();
        
        // Trigger the job to run on the next round minute
        org.quartz.Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("trigger2", "group1")
            .startNow()
            .build();
        
        // Tell quartz to schedule the job using our trigger
        Scheduler schduler = quartzSchdulingService.getSchedulerFactory().getObject();
        
        schduler.scheduleJob(job, trigger);
        schduler.start();
    }
}
