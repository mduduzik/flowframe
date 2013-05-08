package org.flowframe.scheduling.jobs.manager.tests;


import java.util.Date;
import java.util.HashMap;

import javax.transaction.UserTransaction;

import org.flowframe.erp.app.contractmanagement.business.services.IContractManagementJobServices;
import org.flowframe.erp.app.contractmanagement.business.services.impl.ContractManagementJobServicesImpl;
import org.flowframe.scheduling.jobs.InvokeableServiceJob;
import org.flowframe.scheduling.jobs.manager.JobsManagerImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/flowframe/tm.jta-module-context.xml",
/*        "/META-INF/flowframe/jpa.lob.jpacontainer.springdm-module-context.xml",*/
        "/META-INF/core.dao.datasource.mysql-module-context.xml",
        "/META-INF/module-context.xml"})
public class JobsManagerImplTests  {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	private UserTransaction userTransactionManager;

	@Autowired
	private JobsManagerImpl jobManagerImpl;
	
	@Autowired
	private IContractManagementJobServices contractManagementJobServices;	
	
	
	@Before
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(jobManagerImpl);
        Assert.assertNotNull(contractManagementJobServices);
        
        jobManagerImpl.registerSuperService(contractManagementJobServices, new HashMap<String,Object>());
        
        
        userTransactionManager = (UserTransaction) applicationContext.getBean("globalBitronixTransactionManager");
        Assert.assertNotNull(userTransactionManager);
    }	
	
	@After
	public void tearDown() throws Exception {
	}

    @Test
    public void testCallService() throws Exception {
    	InvokeableServiceJob processNewInvoicesJob = new InvokeableServiceJob(ContractManagementJobServicesImpl.class.getName(),"processNewInvoices",new String[]{},new String[]{},new String[]{});
    	Scheduler sch = schedulerFactoryBean.getObject();
    	CronTriggerImpl t = new CronTriggerImpl("0 0 12 * * ?");
    	TriggerFiredBundle fb = new TriggerFiredBundle(JobBuilder.newJob().build(), (OperableTrigger)t, null, false, new Date(), new Date(), new Date(), new Date());

    	JobExecutionContext ctx = new JobExecutionContextImpl(sch,fb,null);
    	processNewInvoicesJob.execute(ctx);
    }
}
