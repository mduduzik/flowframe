package org.flowframe.portal.remote.services.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.mdm.domain.user.User;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/portal-module-context-notx.xml"})
public class LiferayPortalUserServiceImpl3Tests  {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private LiferayPortalServicesImpl lplUserRemoteService;
	
	@Before
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        
        Assert.assertNotNull(lplUserRemoteService);
        
        
        lplUserRemoteService.init();
    }	
	
	@After
	public void tearDown() throws Exception {
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
    
    @Test
    public void testGenerenateTemporaryPassword() throws Exception {
    	Folder fldr = null;
    	boolean isAvailable = lplUserRemoteService.isAvailable();
    	Assert.assertTrue(isAvailable); 
    	
    	User user = lplUserRemoteService.provideUserByEmailAddress("Test", "User3", "test.user3@com.com","TenantOrg3");
    	Assert.assertNotNull(user);
    	
    	String upwd = lplUserRemoteService.generateUnencryptedTemporaryPassword("test.user3@com.com");
    	Assert.assertNotNull(upwd);
    }    
    
    //@Test
    public void testGetOrganization() throws Exception {
    	Folder fldr = null;
    	boolean isAvailable = lplUserRemoteService.isAvailable();
    	Assert.assertTrue(isAvailable); 
    	
    	Organization org = lplUserRemoteService.provideOrganization("UnknownOrg");
    	Assert.assertNotNull(org);
    }  
}
