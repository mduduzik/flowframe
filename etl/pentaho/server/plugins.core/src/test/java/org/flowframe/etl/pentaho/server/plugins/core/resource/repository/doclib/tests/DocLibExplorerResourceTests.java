package org.flowframe.etl.pentaho.server.plugins.core.resource.repository.doclib.tests;

import junit.framework.Assert;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.etl.pentaho.server.plugins.core.MainApplication;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapper;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;


public class DocLibExplorerResourceTests extends JerseyTest {

    private String modelJson = null;

    @Autowired
    private IRemoteDocumentRepository repository;

    private String url;

    @Override
    protected Application configure() {
        // Enable logging.
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        // Create an instance of MyApplication ...
        return new MainApplication()
                // ... and pass "contextConfigLocation" property to Spring integration.
                .property("contextConfigLocation", "classpath:/META-INF/DocLibExplorerResourceTests-module-context.xml");
    }



    @Ignore
    @Test
    public void testGetSubFolders() throws Exception {
/*        Folder fldr = null;
        boolean isAvailable = repository.isAvailable();
        org.junit.Assert.assertTrue(isAvailable);


        Folder parentFldr = provideTenantFolder();
        List<Folder> fldrs = repository.getSubFolders(parentFldr.getFolderId() + "");
        org.junit.Assert.assertNotNull(fldrs);

        List<FileEntry> fes = repository.getFileEntries(parentFldr.getFolderId() + "");
        org.junit.Assert.assertNotNull(fes);*/
    }


    @Ignore
    @Test
    public final void testFileEntryURI() throws Exception {
        //getfileentryuri
        String res = target("/docexplorer/getfileentryuri").queryParam("fileEntryId","28601").request().header("userid", "test").get(String.class);
        Assert.assertNotNull(res);

        //getfileentryuri
        res = target("/docexplorer/getwebdavuri").queryParam("fileEntryId","28601").request().header("userid", "test").get(String.class);
        Assert.assertNotNull(res);
    }
}
