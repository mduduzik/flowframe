package org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps;

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
public class BaseDialogResource {
    protected ServletContext context;

    public static String ATTRIBUTENAME_WORKDIR = "workDir";
    protected File tmpDir;


    @Context
    public void setServletContext(ServletContext context) throws ServletException {
        this.context = context;
        tmpDir = (File) context.getAttribute("javax.servlet.context.tempdir");
        if (tmpDir == null) {
            throw new ServletException("Servlet container does not provide temporary directory");
        }
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


    protected void writeStreamToFile(File dir, InputStream in, String fileName) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        FileOutputStream out = new FileOutputStream(new File(dir,fileName));
        //read from is to buffer
        while((bytesRead = in.read(buffer)) !=-1){
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        //flush OutputStream to write any buffered data to file
        out.flush();
        out.close();
    }
}
