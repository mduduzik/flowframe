package org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps;

import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.ws.rs.core.Context;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseDialogDelegateResource {
    protected ServletContext context;

    public static String ATTRIBUTENAME_WORKDIR = "workDir";
    public static String ATTRIBUTENAME_METADATA = "metadata";
    public static String ATTRIBUTENAME_SAMPLEFILE = "samplefile";

    protected File tmpDir;


    @Context
    public void setServletContext(ServletContext context) throws ServletException {
        this.context = context;

        //-- Init temp directory
        tmpDir = (File) context.getAttribute("javax.servlet.context.tempdir");
        if (tmpDir == null) {
            throw new ServletException("Servlet container does not provide temporary directory");
        }
        //-- Initialize
        init();
    }

    protected void cleanUpWorkingDir() {
        File workDir = (File) getSessionAttribute(ATTRIBUTENAME_WORKDIR);
        if (workDir.exists()) {
            workDir.delete();
        }
    }



    protected void setSessionAttribute(String name, Object value) {
        context.setAttribute(name,value);
    }

    protected Object getSessionAttribute(String name) {
        return context.getAttribute(name);
    }


    protected File writeSampleStreamToFile(InputStream in, String fileName) throws IOException {
        File workDir = (File)getSessionAttribute(ATTRIBUTENAME_WORKDIR);

        byte[] buffer = new byte[1024];
        int bytesRead;
        File fileOut = new File(workDir,fileName);
        FileOutputStream out = new FileOutputStream(fileOut);
        //read from is to buffer
        while((bytesRead = in.read(buffer)) !=-1){
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        //flush OutputStream to write any buffered data to file
        out.flush();
        out.close();

        setSessionAttribute(ATTRIBUTENAME_SAMPLEFILE,fileOut);

        return fileOut;
    }

    public void cacheMetadata(CsvInputMeta inputMeta) {
        setSessionAttribute(ATTRIBUTENAME_METADATA,inputMeta);
    }

    public BaseStepMeta getCachedMetadata() {
        return (BaseStepMeta)getSessionAttribute(ATTRIBUTENAME_METADATA);
    }

    public abstract void init();
}
