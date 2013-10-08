package org.flowframe.documentlibrary.remote.services.impl.tests;


import org.apache.commons.io.IOUtils;
import org.flowframe.documentlibrary.remote.services.impl.LiferayPortalDocumentRepositoryImpl;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.metamodel.dao.services.impl.EntityTypeDAOImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import java.io.*;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/LiferayPortalDocumentRepositorySimpleTests-module-context.xml"})
public class LiferayPortalDocumentRepositorySimpleTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private LiferayPortalDocumentRepositoryImpl docRepoRemoteService;

	
	@BeforeClass
	public static void setUp() throws Exception {
    }	
	
	@AfterClass
	public static void tearDown() throws Exception {
	}

    @Test
    public void testGetSubFolders() throws Exception {
    	Folder fldr = null;
    	boolean isAvailable = docRepoRemoteService.isAvailable();
    	Assert.assertTrue(isAvailable);


        Folder parentFldr = provideTenantFolder();
        List<Folder> fldrs = docRepoRemoteService.getSubFolders(parentFldr.getFolderId() + "");
        Assert.assertNotNull(fldrs);
    }


    protected Folder provideTenantFolder() throws Exception {
        Organization tenant = new Organization();
        tenant.setId(1L);
        tenant.setName("Test");

        Folder etlSamplesFolder = null;

        EntityType et = new EntityType("Organization",
                Organization.class,
                null,
                null,
                null,
                "Organization");
        etlSamplesFolder = (Folder)docRepoRemoteService.provideFolderForEntity(et,tenant.getId());

        return etlSamplesFolder;
    }

    
    public static byte[] getBytes(InputStream is) throws IOException {

        int len;
        int size = 1024;
        byte[] buf;

        if (is instanceof ByteArrayInputStream) {
          size = is.available();
          buf = new byte[size];
          len = is.read(buf, 0, size);
        } else {
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          buf = new byte[size];
          while ((len = is.read(buf, 0, size)) != -1)
            bos.write(buf, 0, len);
          buf = bos.toByteArray();
        }
        return buf;
      }    
}
