package org.flowframe.etl.pentaho.server.plugins.core.resource;

import org.apache.commons.vfs.FileObject;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapper;
import org.flowframe.etl.pentaho.server.plugins.core.utils.repository.doclib.DocLibUtil;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.exception.KettleFileException;
import org.pentaho.di.core.vfs.KettleVFS;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseDelegateResource {
    protected final CustomObjectMapper mapper = new  CustomObjectMapper();

    @Autowired
    protected  IRemoteDocumentRepository ecmService;

    protected ServletContext context;

    public static String ATTRIBUTENAME_WORKDIR = "workDir";
    public static String ATTRIBUTENAME_METADATA = "metadata";
    public static String ATTRIBUTENAME_UIMETADATA = "uimetadata";
    public static String ATTRIBUTENAME_SAMPLEFILE = "samplefile";

    protected File tmpDir;
    protected HttpSession session;

    protected boolean initialized = false;


    protected Map<String,Object> getOptions() {
        HashMap<String, Object> options = new HashMap<String, Object>() {{
            put("docRepositoryService", getDocRepositoryService());
        }};

        return options;
    }

/*
    @Context
    public void setContext(ServletContext context) throws ServletException {
        this.context = context;
        //-- Init temp directory
        tmpDir = (File) this.context.getAttribute("javax.servlet.context.tempdir");
        if (tmpDir == null) {
            throw new ServletException("Servlet container does not provide temporary directory");
        }
    }
*/


    protected File writeStreamToFile(InputStream in, String fileName) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        File fileOut = new File(tmpDir, fileName);
        FileOutputStream out = new FileOutputStream(fileOut);
        //read from is to buffer
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        //flush OutputStream to write any buffered data to file
        out.flush();
        out.close();


        return fileOut;
    }

    protected File writeStreamToTempFile(InputStream in, String fileName) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        File fileOut = new File(tmpDir, fileName+"."+System.currentTimeMillis());
        FileOutputStream out = new FileOutputStream(fileOut);
        //read from is to buffer
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        //flush OutputStream to write any buffered data to file
        out.flush();
        out.close();

        return fileOut;
    }

    protected FileObject writeFileToVFSTemp(InputStream in, String fileName) throws IOException, KettleFileException {
        File file = writeStreamToTempFile(in, fileName);
        return KettleVFS.getFileObject(file.getAbsolutePath());
    }

    /**
     * ECM
     */



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
        etlSamplesFolder = (Folder)ecmService.provideFolderForEntity(et,tenant.getId());

        return etlSamplesFolder;
    }

    protected FileEntry addOrUpdateDocLibFile(String folderId, InputStream in, String fileName, String mimeType) throws Exception {
        FileEntry sampleFileEntry = null;

        boolean fExists = ecmService.fileEntryExists(folderId, fileName);

        File file = writeStreamToFile(in, fileName);
        sampleFileEntry = ecmService.addorUpdateFileEntry(folderId, file, mimeType, fileName, fileName);
        file.delete();

        return sampleFileEntry;
    }

    /**
     *
     * Exception Handling
     *
     */
    protected Map<String, Object> createExceptionMap(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();


        Map<String, Object> resultMap = new HashMap<String,Object>();
        resultMap.put("message",e.getMessage());
        resultMap.put("stacktrace",stacktrace);

        return resultMap;
    }

    protected String formatException(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();


        return stacktrace;
    }

    protected IRemoteDocumentRepository getDocRepositoryService() {
        return ecmService;
    }
}
