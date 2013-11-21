package org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.excelinput;

import org.apache.commons.vfs.FileObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.plugins.core.exception.RequestException;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.BaseDialogDelegateResource;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.spreadsheet.KCell;
import org.pentaho.di.core.spreadsheet.KCellType;
import org.pentaho.di.core.spreadsheet.KSheet;
import org.pentaho.di.core.spreadsheet.KWorkbook;
import org.pentaho.di.core.vfs.KettleVFS;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.excelinput.ExcelInputField;
import org.pentaho.di.trans.steps.excelinput.ExcelInputMeta;
import org.pentaho.di.trans.steps.excelinput.SpreadSheetType;
import org.pentaho.di.trans.steps.excelinput.WorkbookFactory;
import org.pentaho.di.trans.steps.textfileinput.TextFileInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/excelinputmeta")
public class ExcelInputDialogDelegateResource extends BaseDialogDelegateResource {
    private static Class<?> PKG = TextFileInput.class;
    private static PluginRegistry registry = PluginRegistry.getInstance();


    @Path("/onnew")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String onNew(@HeaderParam("userid") String userid) throws IOException, RequestException {
        String res = null;
        try {
            ExcelInputMeta meta = new ExcelInputMeta();
            meta.setSpreadSheetType(SpreadSheetType.POI);
            meta.setDefault();
            meta.allocate(1, 0, 0);
            meta.setParentStepMeta(new StepMeta());
            res = mapper.getFilteredWriter().writeValueAsString(meta);
        } catch (Exception e) {
            throw  new RequestException("Error on /onnew",e);
        }

        return res;
    }

    @Path("/onedit")
         @GET
         @Produces(MediaType.APPLICATION_JSON)
         public String onEdit(@HeaderParam("userid") String userid, @QueryParam("pathId") String pathId) throws Exception {
        String res = null;
        try {
            ExcelInputMeta meta = (ExcelInputMeta) RepositoryUtil.getStep(repository, pathId).getStepMetaInterface();

            res = mapper.getFilteredWriter().writeValueAsString(meta);
        }catch (Exception e) {
            throw  new RequestException("Error on /onedit",e);
        }

        return res;
    }

    @Path("/validatefile")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response onValidateFile(@HeaderParam("userid") String userid,
                                 Map<String,String> params) throws Exception {
        String message = null;
        try {
            String fileEntryId = params.get("fileEntryId");
            String ssType = params.get("spreadSheetType");
            String encoding = params.get("encoding");

            TransMeta transMeta = new TransMeta();
            final String webDavFileUrl = getFileEntryWebDavURI(fileEntryId).toString();
            final String filename = transMeta.environmentSubstitute(webDavFileUrl);
            FileObject fileObject = KettleVFS.getFileObject(filename);

            //try all 3 types
            try {
                WorkbookFactory.getWorkbook(SpreadSheetType.valueOf(ssType), KettleVFS.getFilename(fileObject), encoding);
            }
            catch (Exception e) {
               throw new RequestException("File format unsupported",e);
            }

            message = "File " + fileObject.getName().getBaseName() + " validated successfully";
        }catch (Exception e) {
            throw  new RequestException("System Error on /validatefile",e);
        }

        return Response.ok(message, MediaType.TEXT_PLAIN).build();
    }



    @Path("/ongetfields")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onGetFields(@HeaderParam("userid") String userid, ExcelInputMeta meta) throws Exception {
        String res = null;
        try {
            //Generate metadata
            ExcelInputMeta updatedMetadata = updateFields(meta);



            Map<String, Object> resultMap = new HashMap<String,Object>();
            resultMap.put("results",updatedMetadata.getField().length);
            resultMap.put("rows",updatedMetadata.getField());

            res = mapper.writeValueAsString(resultMap);
        } catch (Exception e) {
            throw  new RequestException("Error on /ongetmetadata",e);
        }

        return res;
    }

    @Path("/ongetsheets")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onGetSheets(@HeaderParam("userid") String userid, ExcelInputMeta meta) throws Exception {
        String res = null;
        try {
            //Generate metadata
            final ExcelInputMeta updatedMetadata = updateSheets(meta);

            Map<String, Object> resultMap = new HashMap<String,Object>();
            List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
            for (int i=0; i<updatedMetadata.getSheetName().length; i++) {
                final String sheetName = updatedMetadata.getSheetName()[i];
                HashMap<String, Object> row = new HashMap<String, Object>();
                row.put("name",sheetName);
                row.put("rowStart",1);
                row.put("columnStart",1);
                row.put("include",true);
                rows.add(row);
            }

            resultMap.put("results",updatedMetadata.getSheetName().length);
            resultMap.put("rows",rows);

            res = mapper.writeValueAsString(resultMap);
        } catch (Exception e) {
            throw  new RequestException("Error on /ongetsheets",e);
        }

        return res;
    }


/*    @Path("/previewdata/{start}/{pageSize}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onPreviewData(@HeaderParam("userid") String userid,
                                @PathParam("start") int start,
                                @PathParam("pageSize") int pageSize,
                                ExcelInputMeta meta) throws Exception {
        String res = null;
        try {
            //res = previewData(meta,start,pageSize);
        } catch (Exception e) {
            throw  new RequestException("Error on /previewdata/{"+start+"}/{"+pageSize+"}",e);
        }
        return res;
    }*/

    @Path("/add/{subDirObjId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onAdd(@HeaderParam("userid") String userid,
                        @PathParam("subDirObjId") String subDirObjId,
                        ExcelInputMeta meta) throws Exception {
        String res = null;
        try {
            String stepPid = registry.getPluginId(StepPluginType.class, meta);
            StepMeta step = new StepMeta(stepPid, meta.getParentStepMeta().getName(), meta);
            //hack: clear parentStepMeta as placeholder
            meta.setParentStepMeta(null);

            String pathID = RepositoryUtil.addStep(repository, subDirObjId, step);
            meta = (ExcelInputMeta)step.getStepMetaInterface();

            res = mapper.writeValueAsString(meta);
        } catch (Exception e) {
            throw  new RequestException("Error on /add/{"+subDirObjId+"}",e);
        }
        return res;
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onSave(@HeaderParam("userid") String userid,
                         @HeaderParam("pathId") String pathId,
                         ExcelInputMeta meta) throws Exception {
        String res = null;
        try {
            String stepPid = registry.getPluginId(StepPluginType.class, meta);
            StepMeta stepMeta = new StepMeta(stepPid, meta.getName(), meta);
            String pathID = RepositoryUtil.saveStep(repository, pathId, stepMeta);

            meta = (ExcelInputMeta)stepMeta.getStepMetaInterface();

            res = mapper.writeValueAsString(meta);
        } catch (Exception e) {
            throw  new RequestException("Error on /save",e);
        }
        return res;
    }

    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response onDelete(@HeaderParam("userid") String userid,
                             Map<String,String> params) throws KettleException, JSONException {
        String pathId = (String)params.get("pathId");
        StepMeta stepMeta = RepositoryUtil.getStep(repository,pathId);

        pathId = RepositoryUtil.deleteStep(repository, pathId);

        return Response.ok("StepMeta " + stepMeta.getName() + " deleted successfully", MediaType.TEXT_PLAIN).build();
    }

    @Path("/uploadsample")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadSampleFile(@FormDataParam("cmd") String command,
                                     @FormDataParam("APC_UPLOAD_PROGRESS") String progress,
                                     @FormDataParam("UPLOAD_IDENTIFIER") String identifier,
                                     @FormDataParam("MAX_FILE_SIZE") String fileSizeStr,

                                     @FormDataParam("path") String path,
                                     @FormDataParam("dir") String dir,
                                     FormDataMultiPart mpFileUpload) throws Exception {
        final JSONObject res = new JSONObject();
        res.put("success","true");

        try {
            //Get file handle
            FormDataBodyPart fileBP = getFileBodyPart("file", mpFileUpload);//from form field of name:file
            String fileName = fileBP.getContentDisposition().getFileName();
            //Long fileSize = Long.valueOf(fileSizeStr);
            String mimeType = fileBP.getContentDisposition().getType();
            InputStream sampleCSVInputStream = fileBP.getValueAs(InputStream.class);

            //Save sample to ECM
            FileEntry fe = addOrUpdateDocLibFile(sampleCSVInputStream, fileName, mimeType);

            //Result
            res.put("fileName",fileName);
            res.put("fileentryid",fe.getFileEntryId());
        } catch (Exception e) {
            e.printStackTrace();
            res.put("success","false");
            res.put("errorMessage",e.getMessage());
            return res.toString();
        }

        return res.toString();

    }

    private FormDataBodyPart getFileBodyPart(String startsWidth_, FormDataMultiPart multiPart) {
        List<FormDataBodyPart> fileBPList = null;
        Map<String, List<FormDataBodyPart>> fields = multiPart.getFields();
        for (String key_ : fields.keySet()) {
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

    //File Analysis


    private ExcelInputMeta updateSheets(ExcelInputMeta meta) throws Exception {
        TransMeta transMeta = new TransMeta();
        transMeta.setName("ExcelInputMetaSheets");

        List<String> sheetnames = new ArrayList<String>();
        int[] startRowIndeces = null; //0-based
        int[] startColumnIndeces = null; //0-based

        FileObject fileObject;

        final String webDavFileUrl = getFileEntryWebDavURI(new URI(meta.getFileName()[0])).toString();
        final String filename = transMeta.environmentSubstitute(webDavFileUrl);
        fileObject = KettleVFS.getFileObject(filename);
        KWorkbook workbook = WorkbookFactory.getWorkbook(meta.getSpreadSheetType(), KettleVFS.getFilename(fileObject), meta.getEncoding());

        int nrSheets = workbook.getNumberOfSheets();
        startRowIndeces = new int[nrSheets];
        startColumnIndeces = new int[nrSheets];
        for (int j=0;j<nrSheets;j++)
        {
            KSheet sheet = workbook.getSheet(j);
            String sheetname = sheet.getName();

            if (Const.indexOfString(sheetname, sheetnames)<0) {
                sheetnames.add(sheetname);
                startRowIndeces[j] = 0;
                startColumnIndeces[j] = 0;
            }
        }

        workbook.close();

        //Update meta
        meta.setSheetName(sheetnames.toArray(new String[]{}));
        meta.setStartRow(startRowIndeces);
        meta.setStartColumn(startColumnIndeces);


        meta.setChanged();

        return meta;
    }

    private ExcelInputMeta updateFields(ExcelInputMeta meta) throws Exception {
        TransMeta transMeta = new TransMeta();
        transMeta.setName("ExcelInputMetaFields");

        RowMetaInterface fields = new RowMeta();

        FileObject fileObject;

        final String webDavFileUrl = getFileEntryWebDavURI(new URI(meta.getFileName()[0])).toString();
        //meta.getFileName()[0] = webDavFileUrl;//for later use
        final String filename = transMeta.environmentSubstitute(webDavFileUrl);
        fileObject = KettleVFS.getFileObject(filename);


        KWorkbook workbook = WorkbookFactory.getWorkbook(meta.getSpreadSheetType(), KettleVFS.getFilename(fileObject), meta.getEncoding());

        int nrSheets = workbook.getNumberOfSheets();
        for (int j=0;j<nrSheets;j++)
        {
            KSheet sheet = workbook.getSheet(j);

            // See if it's a selected sheet:
            int sheetIndex;
            if (meta.readAllSheets())
            {
                sheetIndex = 0;
            }
            else
            {
                sheetIndex = Const.indexOfString(sheet.getName(), meta.getSheetName());
            }
            if (sheetIndex>=0)
            {
                // We suppose it's the complete range we're looking for...
                //
                int rownr=0;
                int startcol=0;

                if (meta.readAllSheets())
                {
                    if (meta.getStartColumn().length==1) startcol=meta.getStartColumn()[0];
                    if (meta.getStartRow().length==1) rownr=meta.getStartRow()[0];
                }
                else
                {
                    rownr=meta.getStartRow()[sheetIndex];
                    startcol = meta.getStartColumn()[sheetIndex];
                }

                boolean stop=false;
                for (int colnr=startcol;colnr<256 && !stop;colnr++)
                {
                    try
                    {
                        String fieldname = null;
                        int    fieldtype = ValueMetaInterface.TYPE_NONE;

                        KCell cell = sheet.getCell(colnr, rownr);
                        if(cell==null) {
                            stop=true;
                        }else {
                            if (cell.getType() != KCellType.EMPTY )
                            {
                                // We found a field.
                                fieldname = cell.getContents();
                            }

                            // System.out.println("Fieldname = "+fieldname);

                            KCell below = sheet.getCell(colnr, rownr + 1);
                            if (below != null) {
                                if (below.getType() == KCellType.BOOLEAN) {
                                    fieldtype = ValueMetaInterface.TYPE_BOOLEAN;
                                } else if (below.getType() == KCellType.DATE) {
                                    fieldtype = ValueMetaInterface.TYPE_DATE;
                                } else if (below.getType() == KCellType.LABEL) {
                                    fieldtype = ValueMetaInterface.TYPE_STRING;
                                } else if (below.getType() == KCellType.NUMBER) {
                                    fieldtype = ValueMetaInterface.TYPE_NUMBER;
                                } else {
                                    fieldtype = ValueMetaInterface.TYPE_STRING;
                                }
                            }
                            else{
                                fieldtype = ValueMetaInterface.TYPE_STRING;
                            }

                            if (fieldname != null && fieldtype != ValueMetaInterface.TYPE_NONE) {
                                ValueMetaInterface field = new ValueMeta(fieldname, fieldtype);
                                fields.addValueMeta(field);
                            } else {
                                if (fieldname == null)
                                    stop = true;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException aioobe)
                    {
                        // System.out.println("index out of bounds at column "+colnr+" : "+aioobe.toString());
                        stop=true;
                    }
                }
            }
        }

        workbook.close();

        int nrFields = fields.size();
        ExcelInputField[] excelFields = new ExcelInputField[nrFields];

        if (fields.size()>0)
        for (int j=0;j<fields.size();j++)
        {
            ValueMetaInterface field = fields.getValueMeta(j);
            excelFields[j] = new ExcelInputField();

            excelFields[j].setName( field.getName() );
            excelFields[j].setType(ValueMeta.getType(field.getTypeDesc()));
            String slength  = "";
            String sprec    = "";
            excelFields[j].setTrimType( ExcelInputMeta.getTrimTypeByDesc("none") );
            excelFields[j].setRepeated( BaseMessages.getString(PKG, "System.Combo.Yes").equalsIgnoreCase("N") );
        }
        meta.setField(excelFields);

        meta.setChanged();

        return meta;
    }



    private static RowMetaInterface createRowMetaInterface() {
        RowMetaInterface rm = new RowMeta();

        ValueMetaInterface[] valuesMeta = {new ValueMeta("filename",
                ValueMeta.TYPE_STRING),};

        for (int i = 0; i < valuesMeta.length; i++) {
            rm.addValueMeta(valuesMeta[i]);
        }

        return rm;
    }

    private static List<RowMetaAndData> createData(String fileName) {
        List<RowMetaAndData> list = new ArrayList<RowMetaAndData>();

        RowMetaInterface rm = createRowMetaInterface();

        Object[] r1 = new Object[]{fileName};

        list.add(new RowMetaAndData(rm, r1));

        return list;
    }

}
