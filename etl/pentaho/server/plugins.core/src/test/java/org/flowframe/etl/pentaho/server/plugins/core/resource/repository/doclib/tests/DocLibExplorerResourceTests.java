package org.flowframe.etl.pentaho.server.plugins.core.resource.repository.doclib.tests;

import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/DocLibExplorerResourceTests-module-context.xml"})
public class DocLibExplorerResourceTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private IRemoteDocumentRepository repository;

	@Before
	public void setUp() throws KettleException{
	}
	
	@After
	public void cleanUp() throws KettleException{
	}

    @Test
    public void testGetSubFolders() throws Exception {
        Folder fldr = null;
        boolean isAvailable = repository.isAvailable();
        org.junit.Assert.assertTrue(isAvailable);


        Folder parentFldr = provideTenantFolder();
        List<Folder> fldrs = repository.getSubFolders(parentFldr.getFolderId() + "");
        org.junit.Assert.assertNotNull(fldrs);
    }


    protected Folder provideTenantFolder() throws Exception {
        Organization tenant = new Organization();
        tenant.setId(1L);
        tenant.setName("Test");

        Folder fldr = null;

        EntityType et = new EntityType("Organization",
                Organization.class,
                null,
                null,
                null,
                "Organization");
        fldr = (Folder)repository.provideFolderForEntity(et,tenant.getId());

        return fldr;
    }
}
