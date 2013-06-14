package org.flowframe.erp.remote.services.impl;


import java.util.List;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;
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
@ContextConfiguration(locations = { "/META-INF/oberp-module-context-notx.xml"})
public class OBERPPartnerServiceImplTests  {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private OBPartnerServicesImpl erpPartnerRemoteService;
	
	@Before
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        
        Assert.assertNotNull(erpPartnerRemoteService);
        
        
        erpPartnerRemoteService.init();
    }	
	
	@After
	public void tearDown() throws Exception {
	}
    
    @Test
    public void testGetOrganization() throws Exception {
/*        String filterClause = "Reference List";
        filterClause = URLEncoder.encode(filterClause, "UTF-8");
        final JSONObject jsonObject = erpPartnerRemoteService.doRequest( OBPartnerServicesImpl.URL_PART + "/ADTable?description=" + filterClause,
            JsonConstants.IDENTIFIER, "GET", 200);
        final JSONArray jsonArray = jsonObject.getJSONObject(JsonConstants.RESPONSE_RESPONSE)
            .getJSONArray(JsonConstants.DATA);  */  	
    	List<Organization> orgs = erpPartnerRemoteService.getAllOrganizations();
    	Assert.assertTrue(orgs != null);
    }  
}
