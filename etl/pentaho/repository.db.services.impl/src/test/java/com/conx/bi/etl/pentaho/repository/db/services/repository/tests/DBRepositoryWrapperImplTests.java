package com.conx.bi.etl.pentaho.repository.db.services.repository.tests;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.conx.bi.etl.pentaho.repository.db.services.CustomRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/DBRepositoryWrapperImplTests-module-context.xml"})
public class DBRepositoryWrapperImplTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private CustomRepository repository;
	private RepositoryDirectoryInterface rootDir;
	private RepositoryDirectoryInterface testDir;
	
	@Before
	public void setUp() throws KettleException{
		Assert.notNull(repository);
		
		//-- Create test folder
		rootDir = repository.findDirectory("/conxbi");
		testDir = repository.createRepositoryDirectory(rootDir, "test");
	}
	
	@After
	public void cleanUp() throws KettleException{
		//-- Delete test folder
		repository.deleteRepositoryDirectory(testDir);
	}
	
	@Test
	public void testCreateTenantDirectory() throws KettleException {
		Organization org = new Organization();
		org.setId(100L);
		org.setName("Test Tenant");
		RepositoryDirectoryInterface dir = repository.provideDirectoryForTenant(org);
		Assert.notNull(dir);
		
		dir = repository.provideInputTransStepDirectoryForTenant(org);
		Assert.notNull(dir);		
	}
	
    
    @Test
    public void testLoadTransMeta() throws Exception {  
        RepositoryDirectoryInterface testDir = repository.findDirectory("/conxbi/library/etl/byproduct/cargowise/warehouse/transforms/product/to/legacy");
        Assert.notNull(testDir);
        
        //loadTransformation(String transname, RepositoryDirectoryInterface repdir, ProgressMonitorListener monitor, boolean setInternalVariables, String revision)
        TransMeta transMeta = repository.loadTransformation("cwEdiProductSubTransform_v1.0.0", testDir, null , false, "1.0.0");
        Assert.notNull(transMeta);
    }
    
    
}
