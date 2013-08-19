package com.conx.bi.etl.pentaho.repository.db.services.repository.tests;

import com.conx.bi.etl.pentaho.repository.db.services.repository.IdentityUtil;
import org.flowframe.etl.pentaho.repository.db.model.DatabaseMetaDTO;
import org.flowframe.etl.pentaho.repository.db.repository.CustomRepository;
import org.flowframe.etl.pentaho.repository.db.repository.DBRepositoryWrapperImpl;
import org.flowframe.etl.pentaho.repository.db.repository.DatabaseMetaUtil;
import org.flowframe.etl.pentaho.repository.db.repository.RepositoryUtil;
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

import java.util.List;

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
            this.repo = DBRepositoryWrapperImpl.getINSTANCE();
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
	public void testBasics() throws KettleException {
        Organization tenant = new Organization();
        tenant.setId(1L);
        tenant.setName("Test");

        try {
            //Add db - dir id(2)/DBConnections
            DatabaseMetaDTO dto = new DatabaseMetaDTO();
            dto.setDirObjectId(2L);
            dto.setName("test1");
            dto.setDatabasePort(20);
            dto.setDatabaseType("MSSQL");

            RepositoryDirectoryInterface dir = RepositoryUtil.getDirectory(repo, new LongObjectId(2L));

            DatabaseMeta db = DatabaseMetaUtil.addDatabaseMeta(tenant, repo, dir, dto);
            Assert.notNull(db);

            //Get tenant dbs
            List<DatabaseMeta> dbs = DatabaseMetaUtil.getDatabasesBySubDirAndTenantId(repo, dir, tenant.getId().toString());
            Assert.notNull(dbs);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
