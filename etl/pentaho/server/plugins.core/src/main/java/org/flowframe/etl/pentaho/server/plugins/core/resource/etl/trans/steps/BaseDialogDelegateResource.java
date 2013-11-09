package org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps;

import org.apache.commons.vfs.FileObject;
import org.flowframe.etl.pentaho.server.plugins.core.resource.BaseDelegateResource;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.pentaho.di.core.exception.KettleFileException;
import org.pentaho.di.core.vfs.KettleVFS;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseDialogDelegateResource extends BaseDelegateResource {

    @Autowired
    protected ICustomRepository repository;

    protected String toExternalURI(String internalURI) {
        return null;
    }

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



    protected FileEntry addOrUpdateDocLibFile(InputStream in, String fileName, String mimeType) throws Exception {
        FileEntry sampleFileEntry = null;

        Folder fldr = provideTenantFolder();
        boolean fExists = ecmService.fileEntryExists(Long.toString(fldr.getFolderId()), fileName);

        File sampleFile = writeStreamToFile(in, fileName);
        sampleFileEntry = ecmService.addorUpdateFileEntry(Long.toString(fldr.getFolderId()), sampleFile, mimeType, fileName, fileName);
        sampleFile.delete();

        return sampleFileEntry;
    }


}
