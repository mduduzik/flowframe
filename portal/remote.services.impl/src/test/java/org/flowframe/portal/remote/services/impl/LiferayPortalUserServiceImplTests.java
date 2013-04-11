package org.flowframe.portal.remote.services.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.mdm.domain.user.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/flowframe/tm.jta-module-context.xml",
        "/META-INF/flowframe/jpa.lob.jpacontainer.springdm-module-context.xml",
        "/META-INF/flowframe/tm.jta-module-context.xml",
        "/META-INF/flowframe/core.dao.datasource.mysql-module-context.xml",
        "/META-INF/portal-module-context.xml"})
public class LiferayPortalUserServiceImplTests  {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private EntityManagerFactory  ffLoBEntityManagerFactory;
	
	private UserTransaction userTransactionManager;

	private EntityManager em;

	@Autowired
	private LiferayPortalServicesImpl lplUserRemoteService;
	
	
	@Before
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(ffLoBEntityManagerFactory);
        
    	em = ffLoBEntityManagerFactory.createEntityManager();
        
        userTransactionManager = (UserTransaction) applicationContext.getBean("globalBitronixTransactionManager");
        Assert.assertNotNull(userTransactionManager);
        
        Assert.assertNotNull(lplUserRemoteService);
        
        
        lplUserRemoteService.init();
    }	
	
	@After
	public void tearDown() throws Exception {
		em.close();
	}

    //@Test
    public void testUsers() throws Exception {
    	Folder fldr = null;
    	boolean isAvailable = lplUserRemoteService.isAvailable();
    	Assert.assertTrue(isAvailable); 
    	
    	List<User> users = lplUserRemoteService.getUsersByCompanyId("10154");
    	Assert.assertNotNull(users);
    	Assert.assertTrue(users.size() > 0);
    }
    
    //@Test
    public void testAddUser() throws Exception {
    	Folder fldr = null;
    	boolean isAvailable = lplUserRemoteService.isAvailable();
    	Assert.assertTrue(isAvailable); 
    	
    	User user = lplUserRemoteService.provideUserByEmailAddress("Test", "User1", "test.user1@com.com");
    	Assert.assertNotNull(user);
    }    
}
