package org.flowframe.erp.app.contractmanagement.business.services.impl.tests;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/flowframe/tm.jta-module-context.xml",
        "/META-INF/flowframe/jpa.lob.jpacontainer.springdm-module-context.xml",
        "/META-INF/flowframe/tm.jta-module-context.xml",
        "/META-INF/flowframe/dao.services.impl-module-context.xml",
        "/META-INF/flowframe/metamodel.dao.services.impl-module-context.xml",
        "/META-INF/core.dao.datasource.h2-module-context.xml",
        "/META-INF/documentlibrary-module-context.xml",
        "/META-INF/portal-module-context.xml",
        "/META-INF/financialmanagement-module-context.xml",
        "/META-INF/paymentprocessor-module-context.xml",
        "/META-INF/contractmanagementjobservicesimpl-module-context.xml"})
public class ContractManagementJobServicesImplTests extends AbstractJUnit4SpringContextTests {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Before
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
    }	
	
	@After
	public void tearDown() throws Exception {
		//em.close();
	}
	
	@Test
    public void testProcessNewInvoices() throws Exception {
    }
}
