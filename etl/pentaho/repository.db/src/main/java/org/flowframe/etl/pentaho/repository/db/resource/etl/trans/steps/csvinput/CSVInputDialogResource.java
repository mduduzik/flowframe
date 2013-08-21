package org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.BaseDialogResource;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/CSVInput")
public class CSVInputDialogResource extends BaseDialogResource{


    @Context
    public void setServletContext(ServletContext context) throws ServletException {
        super.setServletContext(context);

        File workDir = new File(super.tmpDir, "NewCSVInputDialogResourceDir");
        if (!workDir.exists())
            if (!workDir.mkdirs()) {
                throw new ServletException("Unable to create classes temporary directory");
            }
        setSessionAttribute(ATTRIBUTENAME_WORKDIR,workDir);
    }

    @Path("/uploadsample")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSampleFile(@FormDataParam("cmd") String command,
                                     @FormDataParam("APC_UPLOAD_PROGRESS") String progress,
                                     @FormDataParam("UPLOAD_IDENTIFIER") String identifier,
                                     @FormDataParam("MAX_FILE_SIZE") String fileSizeStr,

                                     @FormDataParam("path") String path,
                                     @FormDataParam("dir") String dir,
                                     FormDataMultiPart mpFileUpload) throws InterruptedException, IOException {

        FormDataBodyPart fileBP = getFileBodyPart("ext-gen", mpFileUpload);
        String fileName = fileBP.getContentDisposition().getFileName();
        Long fileSize = Long.valueOf(fileSizeStr);
        InputStream sampleCSVInputStream = fileBP.getValueAs(InputStream.class);

        context.setAttribute("sampleCSVFileName",fileName);
        context.setAttribute("fileSize",fileSize);


        File workDir = (File)getSessionAttribute("workDir");
        writeStreamToFile(workDir,sampleCSVInputStream,fileName);
/*        String uploadedFileLocation = "c://uploadedFiles/" + fileDetail.getFileName();

        // save it
        saveToFile(uploadedInputStream, uploadedFileLocation);*/

        //String output = "File uploaded via Jersey based RESTFul Webservice to: " + uploadedFileLocation;

  /*      JSONObject res = new JSONObject();

        JSONObject resp = new JSONObject();
        resp.put("progress",3);
        res.put("responseText",resp);//jsonErrorText

        return res.toString();*/
/*        long stepSize = fileSize/10;
        int blockCount = 0;
        long bytesProcessed = 1;
        while (blockCount < 10) {
            Thread.sleep(6000);
            bytesProcessed = stepSize*blockCount;

            context.setAttribute("bytesProcessed",bytesProcessed);

            blockCount++;
        }

        bytesProcessed = fileSize;*/

        return Response.status(200).entity("{progress:"+0+",success:true,filelocation:"+workDir.getAbsolutePath()+"}").build();

    }

    @Path("/uploadprogress")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadProgress() throws JSONException {
        JSONObject progressJSon = new JSONObject();
        if (context.getAttribute("fileName") != null)  {
            Object bytes_ = context.getAttribute("bytesProcessed");
            Object fileName_ = context.getAttribute("fileName");
            Object fileSize_ = context.getAttribute("fileSize");
            if (fileName_ != null ) {
                progressJSon.put("bytesTotal",fileSize_);
                progressJSon.put("bytesUploaded",bytes_);
            }
        }

/*        progressJSon.put("bytesTotal","");
        progressJSon.put("bytesUploaded","");
        progressJSon.put("estSec","");
        progressJSon.put("filesUploaded","");
        progressJSon.put("speedAverage","");
        progressJSon.put("speedLast","");
        progressJSon.put("timeLast","");
        progressJSon.put("timeStart","");*/


        progressJSon.put("success",true);

        return progressJSon.toString();
    }

    private FormDataBodyPart getFileBodyPart(String startsWidth_, FormDataMultiPart multiPart) {
        List<FormDataBodyPart> fileBPList = null;
        Map<String,List<FormDataBodyPart>> fields = multiPart.getFields();
        for(String key_ : fields.keySet()){
            //handleInputStream(field.getValueAs(InputStream.class));
            List<FormDataBodyPart> list = fields.get(key_);
            if (key_.indexOf(startsWidth_) >= 0) {
                fileBPList = list;
                break;
            }
        }

        FormDataBodyPart fileBP = fileBPList.get(0);

        return fileBP;
    }
}
