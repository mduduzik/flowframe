package com.conx.bi.etl.pentaho.repository.db.services.repository.tests;

import com.conx.bi.etl.pentaho.repository.db.services.CustomRepository;
import com.conx.bi.etl.pentaho.repository.db.services.repository.RepositoryExporter;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.ui.repository.repositoryexplorer.RepositoryExplorerCallback;
import org.pentaho.di.ui.repository.repositoryexplorer.model.UIRepositoryContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import static com.conx.bi.etl.pentaho.repository.db.services.repository.DBRepositoryWrapperImpl.getINSTANCE;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/META-INF/DBRepositoryWrapperImplTests-module-context.xml"})
public class RepositoryExplorerTests  {

	@Autowired
	private CustomRepository repository;
	private RepositoryDirectoryInterface rootDir;
	private RepositoryDirectoryInterface testDir;
    private CustomRepository repo;

    @Before
	public void setUp() throws KettleException{
        try {
            this.repo = getINSTANCE();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Error e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	
	@After
	public void cleanUp() throws KettleException{
		//-- Delete test folder
        repo.disconnect();
	}
	
	@Test
	public void testCreateJSONRepositoryTreeByTenant() throws KettleException {
        Organization tenant = new Organization();
        tenant.setId(1L);
        tenant.setName("Test");

        try {
            JSONArray res = RepositoryExporter.exportTreeToJSONByTenant(null, repo, tenant);
            Assert.notNull(res);
            String resFmtd = res.toString(1);
            Assert.notNull(resFmtd);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
