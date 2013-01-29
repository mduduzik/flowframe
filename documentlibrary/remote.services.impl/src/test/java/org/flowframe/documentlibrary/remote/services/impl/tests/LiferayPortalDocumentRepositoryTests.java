package org.flowframe.documentlibrary.remote.services.impl.tests;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.apache.commons.io.IOUtils;
import org.flowframe.documentlibrary.remote.services.impl.LiferayPortalDocumentRepositoryImpl;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.metamodel.dao.services.impl.EntityTypeDAOImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;


@ContextConfiguration(locations = { "/META-INF/flowframe/tm.jta-module-context.xml",
		"/META-INF/jpa.lob.jpacontainer.springdm-module-context.xml",
        "/META-INF/flowframe/dao.services.impl-module-context.xml",
        "/META-INF/flowframe/metamodel.dao.services.impl-module-context.xml",        
        "/META-INF/core.dao.datasource.mysql-module-context.xml",
        "/META-INF/doclib-module-context.xml"})
public class LiferayPortalDocumentRepositoryTests {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private EntityManagerFactory  ffEntityManagerFactory;
	
	private UserTransaction userTransactionManager;

	private EntityManager em;
	
	
	@Autowired
	private EntityTypeDAOImpl entityTypeDAOService;
	
	@Autowired
	private LiferayPortalDocumentRepositoryImpl docRepoRemoteService;

	private FileEntry bolFileEntry;
	
	@BeforeClass
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(ffEntityManagerFactory);
        
    	em = ffEntityManagerFactory.createEntityManager();
        
        userTransactionManager = (UserTransaction) applicationContext.getBean("globalBitronixTransactionManager");
        Assert.assertNotNull(userTransactionManager);
        
        Assert.assertNotNull(entityTypeDAOService);
        Assert.assertNotNull(docRepoRemoteService);
        
        
        docRepoRemoteService.init();
    }	
	
	@AfterClass
	public void tearDown() throws Exception {
		em.close();
	}
	
	@Ignore
    @Test
    public void testEnsureFolder() throws Exception {
    	Folder fldr = null;
    	boolean isAvailable = docRepoRemoteService.isAvailable();
    	Assert.assertTrue(isAvailable); 
    	
    	boolean res = docRepoRemoteService.folderExists(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
    	if (!res)
    	{
	    	fldr = docRepoRemoteService.addFolder(docRepoRemoteService.getConxlogiFolderId(),"Receive123", "Receive123");
	    	Assert.assertNotNull(fldr);
	    	
	    	fldr = docRepoRemoteService.getFolderByName(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
	    	Assert.assertNotNull(fldr);
	    	
	    	res = docRepoRemoteService.folderExists(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
	    	Assert.assertTrue(res);    	
    	}
    }
    
    @Ignore
    @Test
    public void testAddAndDeleteFile() throws Exception {
    	Folder fldr = null;
    	boolean isAvailable = docRepoRemoteService.isAvailable();
    	Assert.assertTrue(isAvailable); 
    	
    	fldr = docRepoRemoteService.getFolderByName(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
    	Assert.assertNotNull(fldr);    	
    	
    	boolean res = docRepoRemoteService.fileEntryExists(Long.toString(fldr.getFolderId()),"BoL");
    	if (!res)
    	{
    		InputStream is = new ByteArrayInputStream("test string".getBytes());
    		bolFileEntry = docRepoRemoteService.addFileEntry(Long.toString(fldr.getFolderId()), "src/test/resources/bol.pdf", "application/pdf", "BoL", "BoL");
/*	    	fldr = docRepoRemoteService.addFolder(docRepoRemoteService.getConxlogiFolderId(),"Receive123", "Receive123");
	    	Assert.assertNotNull(fldr);
	    	
	    	fldr = docRepoRemoteService.getFolderByName(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
	    	Assert.assertNotNull(fldr);
	    	
	    	res = docRepoRemoteService.folderExists(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
	    	Assert.assertTrue(res);    	
	    	
	    	docRepoRemoteService.deleteFolderById(Long.toString(fldr.getFolderId()));
	    	
	    	res = docRepoRemoteService.folderExists(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
	    	Assert.assertFalse(res);*/
    	}
    	else
    	{
/*	    	docRepoRemoteService.deleteFolderByName(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
	    	
	    	res = docRepoRemoteService.folderExists(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
	    	Assert.assertFalse(res);  */  		
    	}
    }   
    
    @Ignore
    @Test
    public void testDeleteFolder() throws Exception {
    	Folder fldr = null;
    	boolean isAvailable = docRepoRemoteService.isAvailable();
    	Assert.assertTrue(isAvailable); 
    	
    	boolean res = docRepoRemoteService.folderExists(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
    	if (res)
    	{
	    	docRepoRemoteService.deleteFolderByName(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
	    	
	    	res = docRepoRemoteService.folderExists(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
	    	Assert.assertFalse(res);    		
    	}
    }   
    
    @Ignore
    @Test
    public void testDownloadFileEntryAsStream() throws Exception {
    	Folder fldr = docRepoRemoteService.getFolderByName(docRepoRemoteService.getConxlogiFolderId(),"Receive123");
    	Assert.assertNotNull(fldr);    	
    	

    	bolFileEntry = docRepoRemoteService.getFileEntryByTitle(Long.toString(fldr.getFolderId()),"BoL");
    	Assert.assertNotNull(bolFileEntry);
    	
    	InputStream reportStream = docRepoRemoteService.getFileAsStream(Long.toString(bolFileEntry.getFileEntryId()), "1.0");
    	Assert.assertNotNull(reportStream);
    	
    	try {
    		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("BoL.pdf"));
    	    try {
    	        IOUtils.copy(reportStream, output);
    	    } finally {
    	    	reportStream.close();
    	    	output.close();
    	    }
    	} finally {
    	}
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
