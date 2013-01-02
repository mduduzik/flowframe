package org.flowframe.portal.remote.services.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "/META-INF/tm.jta-module-context.xml",
        "/META-INF/persistence.datasource-module-context.xml",
        "/META-INF/persistence.dynaconfiguration-module-context.xml",
        "/META-INF/mdm.dao.services.impl-module-context.xml",
        "/META-INF/metamodel.dao.jpa.persistence-module-context.xml",
        "/META-INF/module-context.xml"
        })
public class LiferayPortalUserServiceImplTests extends AbstractTestNGSpringContextTests {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private EntityManagerFactory  ffLoBEntityManagerFactory;
	
	private UserTransaction userTransactionManager;

	private EntityManager em;

	@Autowired
	private LiferayPortalServicesImpl lplUserRemoteService;
	
	@BeforeClass
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(ffLoBEntityManagerFactory);
        
    	em = ffLoBEntityManagerFactory.createEntityManager();
        
        userTransactionManager = (UserTransaction) applicationContext.getBean("globalBitronixTransactionManager");
        Assert.assertNotNull(userTransactionManager);
        
        Assert.assertNotNull(lplUserRemoteService);
        
        
        lplUserRemoteService.init();
    }	
	
	@AfterClass
	public void tearDown() throws Exception {
		em.close();
	}

    @Test(enabled=false)
    public void testUsers() throws Exception {
    	Folder fldr = null;
    	boolean isAvailable = lplUserRemoteService.isAvailable();
    	Assert.assertTrue(isAvailable); 
    	
    	List<User> users = lplUserRemoteService.getUsersByCompanyId("10154");
    	Assert.assertNotNull(users);
    	Assert.assertTrue(users.size() > 0);
    }
    
}
