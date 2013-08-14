package com.conx.bi.etl.pentaho.repository.db.services.repository.tests;

import com.conx.bi.etl.pentaho.repository.db.services.CustomRepository;
import com.conx.bi.etl.pentaho.repository.db.services.repository.IdentityUtil;
import com.conx.bi.etl.pentaho.repository.db.services.repository.RepositoryExporter;
import org.codehaus.jettison.json.JSONArray;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import static com.conx.bi.etl.pentaho.repository.db.services.repository.DBRepositoryWrapperImpl.getINSTANCE;


//@Ignore
public class IdentityUtilTests {

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
	public void testCreatePathID() throws KettleException {
        Organization tenant = new Organization();
        tenant.setId(1L);
        tenant.setName("Test");

        try {
            // Step URI
            RepositoryDirectoryInterface delimitedDir = repo.findDirectory(new LongObjectId(5L));
            Assert.notNull(delimitedDir);

            TransMeta delimStepsTrans = repo.loadTransformation(new LongObjectId(2), "null");
            StepMeta step = delimStepsTrans.getStep(0);

            String uri = IdentityUtil.generatePathID(step,0);
            Assert.notNull(uri);

            delimitedDir = IdentityUtil.getDirectory(repo,uri);
            step = IdentityUtil.getStep(repo,uri);

            // DB URI
            RepositoryDirectoryInterface dbDir = repo.findDirectory(new LongObjectId(1L));
            TransMeta connectionsTrans = repo.loadTransformation(new LongObjectId(1), "null");
            DatabaseMeta db = connectionsTrans.getDatabase(0);
            uri = IdentityUtil.generatePathID(connectionsTrans,db);
            Assert.notNull(uri);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
