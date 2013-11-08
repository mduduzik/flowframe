package org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.textfileinput;

import org.apache.commons.vfs.FileObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapper;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.BaseDialogDelegateResource;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.textfileinput.dto.TextFileInputMetaDTO;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.CentralLogStore;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.util.StringEvaluationResult;
import org.pentaho.di.core.util.StringEvaluator;
import org.pentaho.di.core.vfs.KettleVFS;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.*;
import org.pentaho.di.trans.debug.StepDebugMeta;
import org.pentaho.di.trans.debug.TransDebugMeta;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.dummytrans.DummyTransMeta;
import org.pentaho.di.trans.steps.injector.InjectorMeta;
import org.pentaho.di.trans.steps.samplerows.SampleRowsMeta;
import org.pentaho.di.trans.steps.textfileinput.*;
import org.pentaho.hadoop.HadoopCompression;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/textfileinputmeta")
public class TextFileInputDialogDelegateResource extends BaseDialogDelegateResource {
    private final CustomObjectMapper mapper = new  CustomObjectMapper();

    private static Class<?> PKG = TextFileInput.class;
    private static PluginRegistry registry = PluginRegistry.getInstance();
    private TextFileInputField[] cachedInputFields;


    @Path("/onnew")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String onNew(@HeaderParam("userid") String userid) throws IOException {
        TextFileInputMeta meta = new TextFileInputMeta();
        meta.setDefault();
        meta.allocate(1, 0, 0);
        return mapper.getFilteredWriter().writeValueAsString(meta);
    }

    @Path("/onedit")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String onEdit(@HeaderParam("userid") String userid, @QueryParam("pathId") String pathId) throws Exception {

        TextFileInputMeta res = (TextFileInputMeta) RepositoryUtil.getStep(repository, pathId).getStepMetaInterface();

        return mapper.getFilteredWriter().writeValueAsString(res);
    }


    @Path("/ongetmetadata")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onGetMetadata(@HeaderParam("userid") String userid, TextFileInputMeta meta) throws Exception {
        String res = null;
        try {
            //Generate metadata
            TextFileInputMeta updatedMetadata = updateMetadata(meta);

            Map<String, Object> resultMap = new HashMap<String,Object>();
            resultMap.put("results",updatedMetadata.getInputFields().length);
            resultMap.put("rows",updatedMetadata.getInputFields());

            res = mapper.writeValueAsString(resultMap);
        } catch (Exception e) {
            res = mapper.writeValueAsString(createExceptionMap(e));
            e.printStackTrace();
        }

        return res;
    }

    @Path("/previewdata")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onPreviewData(@HeaderParam("userid") String userid,
                                @HeaderParam("start") int start,
                                @HeaderParam("pageSize") int pageSize,
                                TextFileInputMeta meta) throws Exception {
        String res = null;
        try {
        res = previewData(meta,start,pageSize);
        } catch (Exception e) {
            res = mapper.writeValueAsString(createExceptionMap(e));
            e.printStackTrace();
        }
        return res;
    }

    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onAdd(@HeaderParam("userid") String userid,
                        TextFileInputMetaDTO metaDTO_) throws Exception {
        TextFileInputMeta meta_ = (TextFileInputMeta) metaDTO_.fromDTO(TextFileInputMeta.class);
        String stepPid = registry.getPluginId(StepPluginType.class, meta_);
        StepMeta step = new StepMeta(stepPid, metaDTO_.getName(), meta_);

        String pathID = RepositoryUtil.addStep(repository, metaDTO_.getSubDirObjId(), step);

        meta_ = (TextFileInputMeta)step.getStepMetaInterface();
        //meta_.setFilename(metaDTO_.getFileEntryId());

        TextFileInputMetaDTO dto = new TextFileInputMetaDTO(meta_);
        dto.setName(step.getName());
        dto.setPathId(pathID);

        return dto.toJSON();
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onSave(@HeaderParam("userid") String userid,
                         TextFileInputMetaDTO metaDTO_) throws Exception {
        TextFileInputMeta meta_ = (TextFileInputMeta) metaDTO_.fromDTO(TextFileInputMeta.class);
        String stepPid = registry.getPluginId(StepPluginType.class, meta_);
        StepMeta stepMeta = new StepMeta(stepPid, metaDTO_.getName(), meta_);
        String pathID = RepositoryUtil.saveStep(repository, metaDTO_.getPathId(), stepMeta);

        meta_ = (TextFileInputMeta)stepMeta.getStepMetaInterface();

        TextFileInputMetaDTO dto = new TextFileInputMetaDTO(meta_);
        dto.setName(stepMeta.getName());
        dto.setPathId(pathID);

        return dto.toJSON();
    }

    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response onDelete(@HeaderParam("userid") String userid, TextFileInputMetaDTO metaDTO_) throws KettleException, JSONException {
        String pathID = RepositoryUtil.deleteStep(repository, metaDTO_.getPathId());

        return Response.ok("StepMeta " + metaDTO_.getName() + " deleted successfully", MediaType.TEXT_PLAIN).build();
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
    public TextFileInputMeta updateMetadata(TextFileInputMeta inputMetadata) throws Exception {
        FileObject fileObject = null;
        InputStreamReader reader = null;
        TextFileInputMeta outputMetadata = null;
        //TextFileInputMeta cachedMetadata;
        try {
            LogChannelInterface log = null;

            outputMetadata = (TextFileInputMeta)inputMetadata.clone();

            TransMeta transMeta = new TransMeta();
            transMeta.setName("TextFileInputMeta");
            log = new LogChannel("TextFileInputMeta[]");

            //Get file object
            String filename = transMeta.environmentSubstitute(inputMetadata.getFileName()[0]);
            fileObject = KettleVFS.getFileObject(filename);

            String delimiter = transMeta.environmentSubstitute(inputMetadata.getSeparator());
            String enclosure = transMeta.environmentSubstitute(inputMetadata.getEnclosure());

            InputStream      fileInputStream = null;
            ZipInputStream zipInputStream = null ;
            GZIPInputStream  gzipInputStream = null ;
            InputStream      inputStream  = null;
            StringBuilder     lineStringBuilder = new StringBuilder(256);
            int              fileFormatType = inputMetadata.getFileFormatTypeNr();


            fileInputStream = KettleVFS.getInputStream(fileObject);


            if (inputMetadata.getFileCompression() != null) {
                if (inputMetadata.getFileCompression().equals("Zip"))
                {
                    zipInputStream = new ZipInputStream(fileInputStream);
                    zipInputStream.getNextEntry();
                    inputStream=zipInputStream;
                }
                else if (inputMetadata.getFileCompression().equals("GZip"))
                {
                    gzipInputStream = new GZIPInputStream(fileInputStream);
                    inputStream=gzipInputStream;
                }
                else if (inputMetadata.getFileCompression().equals("Hadoop-snappy") &&
                        HadoopCompression.isHadoopSnappyAvailable())
                {
                    try {
                        inputStream = HadoopCompression.getSnappyInputStream(fileInputStream);
                    } catch (Exception ex) {
                        throw new IOException(ex.fillInStackTrace());
                    }
                }
                else
                {
                    inputStream=fileInputStream;
                }
            }
            else {
                inputStream=fileInputStream;
            }

            if (inputMetadata.getEncoding()!=null && inputMetadata.getEncoding().length()>0)
            {
                reader = new InputStreamReader(inputStream, inputMetadata.getEncoding());
            }
            else
            {
                reader = new InputStreamReader(inputStream);
            }

            EncodingType encodingType = EncodingType.guessEncodingType(reader.getEncoding());
            // Read a line of data to determine the number of rows...


            // Scan the header-line, determine fields...
            String line = TextFileInput.getLine(log, reader, encodingType, fileFormatType, lineStringBuilder);
            String[] fields = TextFileInput.guessStringsFromLine(log, line, inputMetadata, delimiter);

            if (inputMetadata.hasHeader())
            {
                for (int i = 0; i < fields.length; i++)
                {
                    String field = fields[i];
                    if (field == null || field.length() == 0)
                    {
                        field = "Field" + (i + 1);
                    }
                    else
                    {
                        // Trim the field
                        field = Const.trim(field);
                        // Replace all spaces & - with underscore _
                        field = Const.replace(field, " ", "_");
                        field = Const.replace(field, "-", "_");
                    }
                }
            }
            else {
                if (!Const.isEmpty(enclosure)) {
                    for (int i = 0; i < fields.length; i++) {
                        if (fields[i].startsWith(enclosure) && fields[i].endsWith(enclosure) && fields[i].length() > 1)
                            fields[i] = fields[i].substring(1, fields[i].length() - 1);
                    }
                }
            }

            // Copy it...
            // Clean-up UI metadata: Trim the names to make sure...
            inputMetadata.setInputFields(new TextFileInputField[fields.length]);
            for (int i = 0; i < fields.length; i++) {
                fields[i] = Const.trim(fields[i]);
                TextFileInputField ifm = new TextFileInputField();
                ifm.setName(fields[i]);
                ifm.setType(ValueMetaInterface.TYPE_STRING);
                inputMetadata.getInputFields()[i] = ifm;
            }

            int samples = 100;
            if (samples >= 0) {
                //Update cached metadata; fields etc. (before a detailed file analysis) from UI metadata
                updateMetadata(outputMetadata, inputMetadata);

                TextFileInputAnalyzer analyzer = new TextFileInputAnalyzer(outputMetadata, transMeta, reader, samples, true);
                String message = analyzer.analyze();
                if (message != null) {
                    //wFields.removeAll();

                    // OK, what's the result of our search?
/*                    getData(meta, false);
                    wFields.removeEmptyRows();
                    wFields.setRowNums();
                    wFields.optWidth(true);

                    EnterTextDialog etd = new EnterTextDialog(shell, BaseMessages.getString(PKG, "TextFileInputDialog.ScanResults.DialogTitle"), BaseMessages.getString(PKG, "TextFileInputDialog.ScanResults.DialogMessage"), message, true);
                    etd.setReadOnly();
                    etd.open();*/
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        } catch (Error e) {
            // TODO Auto-generated catch block
            throw e;
        }
        finally {
            if (reader != null)
                reader.close();
        }

        return outputMetadata;
    }

    private void updateMetadata(TextFileInputMeta meta, TextFileInputMeta inputMetadata) {
        //if (isReceivingInput) {
        meta.setFilenameField(inputMetadata.getFilenameField());
        meta.setIncludeFilename(inputMetadata.includeFilename());
        meta.setSeparator(inputMetadata.getSeparator());
        meta.setEnclosure(inputMetadata.getEnclosure());
        meta.setHeader(inputMetadata.hasHeader());
        meta.setRowNumberField(inputMetadata.getRowNumberField());
        meta.setAddResultFile(inputMetadata.isAddResultFile());
        meta.setEncoding(inputMetadata.getEncoding());


        int nrNonEmptyFields = inputMetadata.getInputFields().length;
        int nrFilters = (inputMetadata.getFilter() == null)?0:inputMetadata.getFilter().length;
        meta.allocate(1,nrNonEmptyFields,nrFilters);

        meta.setFileName(inputMetadata.getFileName());

        for (int i = 0; i < nrNonEmptyFields; i++) {
            meta.getInputFields()[i] = new TextFileInputField();

            int colnr = 1;
            meta.getInputFields()[i].setName(inputMetadata.getInputFields()[i].getName());
            meta.getInputFields()[i].setType(inputMetadata.getInputFields()[i].getType());
            meta.getInputFields()[i].setFormat(inputMetadata.getInputFields()[i].getFormat());
            meta.getInputFields()[i].setLength(inputMetadata.getInputFields()[i].getLength());
            meta.getInputFields()[i].setPrecision(inputMetadata.getInputFields()[i].getPrecision());
            meta.getInputFields()[i].setCurrencySymbol(inputMetadata.getInputFields()[i].getCurrencySymbol());
            meta.getInputFields()[i].setDecimalSymbol(inputMetadata.getInputFields()[i].getDecimalSymbol());
            meta.getInputFields()[i].setGroupSymbol(inputMetadata.getInputFields()[i].getGroupSymbol());
            meta.getInputFields()[i].setTrimType(inputMetadata.getInputFields()[i].getTrimType());
        }
/*        wFields.removeEmptyRows();
        wFields.setRowNums();
        wFields.optWidth(true);*/

        meta.setChanged();
    }


    public String previewData(TextFileInputMeta inputMetadata,int start,int pageSize) throws Exception {
        FileObject fileObject = null;
        InputStreamReader reader = null;
        TextFileInputMeta outputMetadata = null;
        String res = null;
        //TextFileInputMeta cachedMetadata;
        try {
            LogChannelInterface log = null;
            outputMetadata = (TextFileInputMeta)inputMetadata.clone();


            /**
             *
             * generate rows and metadata
             * Example/model:
             *{
             "metaData": [
                 {
                     "name": "KEY",
                     "typeDesc": "String",
                     "type": 2
                 }
                 ],
             "results": 4,
             "totalProperty": "results",
             "root": "rows",
             "rows": [
                 {
                    "KEY": "abc"
                 },
                 {
                    "KEY": "ABC"
                 },
                 {
                    "KEY": "abc"
                 },
                 {
                    "KEY": "ABC"
                 }
                 ]
             }
             */
            List<RowMetaAndData> dataAndMetaRows = generatePreviewDataFromFile(inputMetadata, "TextFileInputPreview",start,pageSize);
            res = mapper.writeValueAsString(dataAndMetaRows);
        } catch (Exception e) {
            throw e;
        }

        return res;
    }


    /**
     *
     * CSV Tools
     *
     *
     */

    /**
     * CSV Analyzer
     */
    public class TextFileInputAnalyzer {
        private Class<?> PKG = TextFileInputMeta.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

        private InputFileMetaInterface meta;

        private int samples;

        private boolean replaceMeta;

        private String message;

        private String debug;

        private long rownumber;

        private InputStreamReader reader;

        private TransMeta transMeta;

        private LogChannelInterface log;

        private EncodingType encodingType;

        /**
         * Creates a new dialog that will handle the wait while we're finding out what tables, views etc we can reach in the
         * database.
         */
        public TextFileInputAnalyzer(InputFileMetaInterface meta, TransMeta transMeta, InputStreamReader reader, int samples, boolean replaceMeta) {
            this.meta = meta;
            this.reader = reader;
            this.samples = samples;
            this.replaceMeta = replaceMeta;
            this.transMeta = transMeta;

            message = null;
            debug = "init";
            rownumber = 1L;

            this.log = new LogChannel(transMeta);

            this.encodingType = EncodingType.guessEncodingType(reader.getEncoding());

        }


        private String analyze() throws Exception {
            // Show information on items using a dialog box
            //
            StringBuilder message = null;
            try {
                String line = "";
                long fileLineNumber = 0;

                DecimalFormatSymbols dfs = new DecimalFormatSymbols();

                int nrfields = meta.getInputFields().length;


                RowMetaInterface outputRowMeta = new RowMeta();
                meta.getFields(outputRowMeta, null, null, null, transMeta);

                // Remove the storage meta-data (don't go for lazy conversion during scan)
                for (ValueMetaInterface valueMeta : outputRowMeta.getValueMetaList()) {
                    valueMeta.setStorageMetadata(null);
                    valueMeta.setStorageType(ValueMetaInterface.STORAGE_TYPE_NORMAL);
                }

                RowMetaInterface convertRowMeta = outputRowMeta.clone();
                for (int i=0;i<convertRowMeta.size();i++) convertRowMeta.getValueMeta(i).setType(ValueMetaInterface.TYPE_STRING);

                // How many null values?
                int nrnull[] = new int[nrfields]; // How many times null value?

                // String info
                String minstr[] = new String[nrfields]; // min string
                String maxstr[] = new String[nrfields]; // max string
                boolean firststr[] = new boolean[nrfields]; // first occ. of string?

                // Date info
                boolean isDate[] = new boolean[nrfields]; // is the field perhaps a Date?
                int dateFormatCount[] = new int[nrfields]; // How many date formats work?
                boolean dateFormat[][] = new boolean[nrfields][Const.getDateFormats().length]; // What are the date formats that
                // work?
                Date minDate[][] = new Date[nrfields][Const.getDateFormats().length]; // min date value
                Date maxDate[][] = new Date[nrfields][Const.getDateFormats().length]; // max date value

                // Number info
                boolean isNumber[] = new boolean[nrfields]; // is the field perhaps a Number?
                int numberFormatCount[] = new int[nrfields]; // How many number formats work?
                boolean numberFormat[][] = new boolean[nrfields][Const.getNumberFormats().length]; // What are the number format that work?
                double minValue[][] = new double[nrfields][Const.getDateFormats().length]; // min number value
                double maxValue[][] = new double[nrfields][Const.getDateFormats().length]; // max number value
                int numberPrecision[][] = new int[nrfields][Const.getNumberFormats().length]; // remember the precision?
                int numberLength[][] = new int[nrfields][Const.getNumberFormats().length]; // remember the length?

                for (int i = 0; i < nrfields; i++)
                {
                    TextFileInputField field = meta.getInputFields()[i];

                    if (log.isDebug()) debug = "init field #" + i;

                    if (replaceMeta) // Clear previous info...
                    {
                        field.setName(meta.getInputFields()[i].getName());
                        field.setType(meta.getInputFields()[i].getType());
                        field.setFormat("");
                        field.setLength(-1);
                        field.setPrecision(-1);
                        field.setCurrencySymbol(dfs.getCurrencySymbol());
                        field.setDecimalSymbol("" + dfs.getDecimalSeparator());
                        field.setGroupSymbol("" + dfs.getGroupingSeparator());
                        field.setNullString("-");
                        field.setTrimType(ValueMetaInterface.TRIM_TYPE_NONE);
                    }

                    nrnull[i] = 0;
                    minstr[i] = "";
                    maxstr[i] = "";
                    firststr[i] = true;

                    // Init data guess
                    isDate[i] = true;
                    for (int j = 0; j < Const.getDateFormats().length; j++)
                    {
                        dateFormat[i][j] = true;
                        minDate[i][j] = Const.MAX_DATE;
                        maxDate[i][j] = Const.MIN_DATE;
                    }
                    dateFormatCount[i] = Const.getDateFormats().length;

                    // Init number guess
                    isNumber[i] = true;
                    for (int j = 0; j < Const.getNumberFormats().length; j++)
                    {
                        numberFormat[i][j] = true;
                        minValue[i][j] = Double.MAX_VALUE;
                        maxValue[i][j] = -Double.MAX_VALUE;
                        numberPrecision[i][j] = -1;
                        numberLength[i][j] = -1;
                    }
                    numberFormatCount[i] = Const.getNumberFormats().length;
                }

                InputFileMetaInterface strinfo = (InputFileMetaInterface) meta.clone();
                for (int i = 0; i < nrfields; i++)
                    strinfo.getInputFields()[i].setType(ValueMetaInterface.TYPE_STRING);

                // Sample <samples> rows...
                debug = "get first line";

                StringBuilder lineBuffer = new StringBuilder(256);
                int fileFormatType = meta.getFileFormatTypeNr();

                // If the file has a header we overwrite the first line
                // However, if it doesn't have a header, take a new line
                //

                line = TextFileInput.getLine(log, reader, encodingType, fileFormatType, lineBuffer);
                fileLineNumber++;
                int skipped=1;

                if (meta.hasHeader())
                {

                    while (line!=null && skipped<meta.getNrHeaderLines())
                    {
                        line = TextFileInput.getLine(log, reader, encodingType, fileFormatType, lineBuffer);
                        skipped++;
                        fileLineNumber++;
                    }
                }
                int linenr = 1;

                List<StringEvaluator> evaluators = new ArrayList<StringEvaluator>();

                // Allocate number and date parsers
                DecimalFormat df2 = (DecimalFormat) NumberFormat.getInstance();
                DecimalFormatSymbols dfs2 = new DecimalFormatSymbols();
                SimpleDateFormat daf2 = new SimpleDateFormat();

                boolean errorFound = false;
                while (!errorFound && line != null && (linenr <= samples || samples == 0))
                {
                    if (log.isDebug()) debug = "convert line #" + linenr + " to row";
                    RowMetaInterface rowMeta = new RowMeta();
                    meta.getFields(rowMeta, "stepname", null, null, transMeta);
                    // Remove the storage meta-data (don't go for lazy conversion during scan)
                    for (ValueMetaInterface valueMeta : rowMeta.getValueMetaList()) {
                        valueMeta.setStorageMetadata(null);
                        valueMeta.setStorageType(ValueMetaInterface.STORAGE_TYPE_NORMAL);
                    }

                    String delimiter = transMeta.environmentSubstitute(meta.getSeparator());
                    Object[] r = TextFileInput.convertLineToRow(log, new TextFileLine(line, fileLineNumber, null), strinfo, null, 0, outputRowMeta, convertRowMeta, meta.getFilePaths(transMeta)[0], rownumber, delimiter, null,
                            false, false, false, false, false, false, false, false,
                            null, null, false, null, null, null, null, 0);

                    if(r == null )
                    {
                        errorFound = true;
                        continue;
                    }
                    rownumber++;
                    for (int i = 0; i < nrfields && i < r.length; i++)
                    {
                        StringEvaluator evaluator;
                        if (i>=evaluators.size()) {
                            evaluator=new StringEvaluator(true);
                            evaluators.add(evaluator);
                        } else {
                            evaluator=evaluators.get(i);
                        }

                        String string = rowMeta.getString(r, i);

                        if (i==0) {
                            System.out.println();
                        }
                        evaluator.evaluateString(string);
                    }

                    fileLineNumber++;
                    if (r!=null) {
                        linenr++;
                    }

                    // Grab another line...
                    //
                    line = TextFileInput.getLine(log, reader, encodingType, fileFormatType, lineBuffer);
                }

                message = new StringBuilder();
                message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.ResultAfterScanning", "" + (linenr - 1)));
                message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.HorizontalLine"));
                for (int i = 0; i < nrfields; i++)
                {
                    TextFileInputField field = meta.getInputFields()[i];
                    StringEvaluator evaluator = evaluators.get(i);
                    List<StringEvaluationResult> evaluationResults = evaluator.getStringEvaluationResults();

                    // If we didn't find any matching result, it's a String...
                    //
                    StringEvaluationResult result = evaluator.getAdvicedResult();
                    if (evaluationResults.isEmpty()) {
                        field.setType(ValueMetaInterface.TYPE_STRING);
                        field.setLength(evaluator.getMaxLength());
                    }
                    if(result != null) {
                        // Take the first option we find, list the others below...
                        //
                        ValueMetaInterface conversionMeta = result.getConversionMeta();
                        field.setType(conversionMeta.getType());
                        field.setTrimType(conversionMeta.getTrimType());
                        field.setFormat(conversionMeta.getConversionMask());
                        field.setDecimalSymbol(conversionMeta.getDecimalSymbol());
                        field.setGroupSymbol(conversionMeta.getGroupingSymbol());
                        field.setLength(conversionMeta.getLength());

                        nrnull[i] = result.getNrNull();
                        minstr[i] = result.getMin() == null ? "" : result.getMin().toString();
                        maxstr[i] = result.getMax() == null ? "" : result.getMax().toString();
                    }


                    message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.FieldNumber", ""+(i + 1)));

                    message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.FieldName", field.getName()));
                    message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.FieldType", field.getTypeDesc()));

                    switch (field.getType())
                    {
                        case ValueMetaInterface.TYPE_NUMBER:
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.EstimatedLength", (field.getLength() < 0 ? "-" : "" + field.getLength())));
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.EstimatedPrecision", field.getPrecision() < 0 ? "-" : "" + field.getPrecision()));
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.NumberFormat", field.getFormat()));

                            if(!evaluationResults.isEmpty()) {
                                if(evaluationResults.size() > 1) {
                                    message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.WarnNumberFormat"));
                                }

                                for(StringEvaluationResult seResult : evaluationResults) {
                                    String mask = seResult.getConversionMeta().getConversionMask();

                                    message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.NumberFormat2", mask));
                                    message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.TrimType", seResult.getConversionMeta().getTrimType()));
                                    message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.NumberMinValue", seResult.getMin()));
                                    message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.NumberMaxValue", seResult.getMax()));

                                    try
                                    {
                                        df2.applyPattern(mask);
                                        df2.setDecimalFormatSymbols(dfs2);
                                        double mn = df2.parse(seResult.getMin().toString()).doubleValue();
                                        message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.NumberExample", mask, seResult.getMin(), Double.toString(mn)));
                                    }
                                    catch (Exception e)
                                    {
                                        if (log.isDetailed()) log.logDetailed("This is unexpected: parsing [" + seResult.getMin() + "] with format [" + mask + "] did not work.");
                                    }
                                }
                            }
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.NumberNrNullValues", ""+nrnull[i]));
                            break;
                        case ValueMetaInterface.TYPE_STRING:
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.StringMaxLength", ""+field.getLength()));
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.StringMinValue", minstr[i]));
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.StringMaxValue", maxstr[i]));
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.StringNrNullValues", ""+nrnull[i]));
                            break;
                        case ValueMetaInterface.TYPE_DATE:
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.DateMaxLength", field.getLength() < 0 ? "-" : "" + field.getLength()));
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.DateFormat", field.getFormat()));
                            if (dateFormatCount[i] > 1)
                            {
                                message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.WarnDateFormat"));
                            }
                            if (!Const.isEmpty(minstr[i]))
                            {
                                for (int x = 0; x < Const.getDateFormats().length; x++)
                                {
                                    if (dateFormat[i][x])
                                    {
                                        message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.DateFormat2", Const.getDateFormats()[x]));
                                        Date mindate = minDate[i][x];
                                        Date maxdate = maxDate[i][x];
                                        message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.DateMinValue", mindate.toString()));
                                        message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.DateMaxValue", maxdate.toString()));

                                        daf2.applyPattern(Const.getDateFormats()[x]);
                                        try
                                        {
                                            Date md = daf2.parse(minstr[i]);
                                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.DateExample", Const.getDateFormats()[x], minstr[i], md.toString()));
                                        }
                                        catch (Exception e)
                                        {
                                            if (log.isDetailed()) log.logDetailed("This is unexpected: parsing [" + minstr[i] + "] with format [" + Const.getDateFormats()[x] + "] did not work.");
                                        }
                                    }
                                }
                            }
                            message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.DateNrNullValues", ""+nrnull[i]));
                            break;
                        default:
                            break;
                    }
                    if (nrnull[i] == linenr - 1)
                    {
                        message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.AllNullValues"));
                    }
                    message.append(Const.CR);
                }
            } catch (Exception e) {
                throw e;
            } catch (Error e) {
                throw e;
            }
            return message.toString();
        }
    }

    /**
     * CSV Data Previewer
     */
    public class TextFileInputPreviewer {
        private Class<?> PKG = TextFileInputMeta.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

        private InputFileMetaInterface meta;

        private final String[] previewStepNames;

        private final int[] previewSize;

        private InputStreamReader reader;

        private TransMeta transMeta;

        private LogChannelInterface log;

        private EncodingType encodingType;

        private String csvInputPreview = "TextFileInputPreview";
        private TransDebugMeta transDebugMeta;
        private String loggingText;
        private Trans trans;

        /**
         * Creates a new dialog that will handle the wait while we're finding out what tables, views etc we can reach in the
         * database.
         */
        public TextFileInputPreviewer(TextFileInputMeta inputMeta, InputStreamReader sampleFileReader) {
            this.meta = inputMeta;
            this.reader = sampleFileReader;
            this.transMeta = new TransMeta();
            this.previewStepNames = new String[]{"TextFileInputPreview", "dummy"};
            this.previewSize = new int[]{100, 100};
        }


        private String preview() throws KettleException {
            // Show information on items using a dialog box
            //
            StringBuilder message = null;
            try {
                TransMeta previewMeta = TransPreviewFactory.generatePreviewTransformation(transMeta, this.meta, csvInputPreview);
                transMeta.getVariable("Internal.Transformation.Filename.Directory");
                previewMeta.getVariable("Internal.Transformation.Filename.Directory");

                this.trans = new Trans(transMeta);

                // Prepare the execution...
                //
                try {
                    trans.prepareExecution(null);
                } catch (final KettleException e) {
                    throw e;
                }

                // Add the preview / debugging information...
                //
                this.transDebugMeta = new TransDebugMeta(previewMeta);
                for (int i = 0; i < previewStepNames.length; i++) {
                    StepMeta stepMeta = previewMeta.findStep(previewStepNames[i]);
                    StepDebugMeta stepDebugMeta = new StepDebugMeta(stepMeta);
                    stepDebugMeta.setReadingFirstRows(true);
                    stepDebugMeta.setRowCount(previewSize[i]);
                    transDebugMeta.getStepDebugMetaMap().put(stepMeta, stepDebugMeta);
                }

                // set the appropriate listeners on the transformation...
                //
                transDebugMeta.addRowListenersToTransformation(trans);

                // Fire off the step threads... start running!
                //
                try {
                    trans.startThreads();
                } catch (final KettleException e) {
                    throw e;
                }

                trans.stopAll();

                // Capture preview activity to a String:
                this.loggingText = CentralLogStore.getAppender().getBuffer(trans.getLogChannel().getLogChannelId(), true).toString();
            } catch (KettleException e) {
                throw e;
            }

            if (message != null)
                return message.toString();
            else
                return null;
        }

        /**
         * @param stepname the name of the step to get the preview rows for
         * @return A list of rows as the result of the preview run.
         */
        public List<Object[]> getPreviewRows(String stepname) {
            if (transDebugMeta == null) return null;

            for (StepMeta stepMeta : transDebugMeta.getStepDebugMetaMap().keySet()) {
                if (stepMeta.getName().equals(stepname)) {
                    StepDebugMeta stepDebugMeta = transDebugMeta.getStepDebugMetaMap().get(stepMeta);
                    return stepDebugMeta.getRowBuffer();
                }
            }
            return null;
        }

        /**
         * @param stepname the name of the step to get the preview rows for
         * @return A description of the row (metadata)
         */
        public RowMetaInterface getPreviewRowsMeta(String stepname) {
            if (transDebugMeta == null) return null;

            for (StepMeta stepMeta : transDebugMeta.getStepDebugMetaMap().keySet()) {
                if (stepMeta.getName().equals(stepname)) {
                    StepDebugMeta stepDebugMeta = transDebugMeta.getStepDebugMetaMap().get(stepMeta);
                    return stepDebugMeta.getRowBufferMeta();
                }
            }
            return null;
        }

        /**
         * @return The logging text from the latest preview run
         */
        public String getLoggingText() {
            return loggingText;
        }

        /**
         * @return The transformation object that executed the preview TransMeta
         */
        public Trans getTrans() {
            return trans;
        }

        /**
         * @return the transDebugMeta
         */
        public TransDebugMeta getTransDebugMeta() {
            return transDebugMeta;
        }
    }


    public static final List<RowMetaAndData> generatePreviewDataFromFile(TextFileInputMeta cim, String oneStepname, int start, int stepSize) throws KettleException {
        KettleEnvironment.init();

        start = (start <= 0)?1:start;
        stepSize = (stepSize <= 0)?100:stepSize;

        cim.setFilenameField("filename");
        cim.setIncludeFilename(true);

        //
        // Create a new transformation...
        //
        TransMeta transMeta = new TransMeta();
        transMeta.setName(oneStepname + "TransMeta");

        //
        // create an injector step...
        //
        String injectorStepname = "DataInjectorStep";
        InjectorMeta im = new InjectorMeta();

        // Set the information of the injector.
        String injectorPid = registry.getPluginId(StepPluginType.class, im);
        StepMeta injectorStep = new StepMeta(injectorPid, injectorStepname, im);
        transMeta.addStep(injectorStep);

        //
        // Create a Csv Input step
        //
        String csvInputName = oneStepname + "Plugin";
        String csvInputPid = registry.getPluginId(StepPluginType.class, cim);
        StepMeta csvInputStep = new StepMeta(csvInputPid, csvInputName, cim);
        transMeta.addStep(csvInputStep);
        TransHopMeta hi = new TransHopMeta(injectorStep, csvInputStep);
        transMeta.addTransHop(hi);

        //
        // Create sample step
        //
        cim.setIncludeRowNumber(true);
        cim.setRowNumberField("lineNumber");
        String sortRowsStepname = "sample rows step";
        SampleRowsMeta srm = new SampleRowsMeta();
        srm.setLineNumberField("lineNumber");
        srm.setLinesRange(start+".."+(start+stepSize));
        String sortRowsStepPid = registry.getPluginId(StepPluginType.class, srm);
        StepMeta sampleRowsStep = new StepMeta(sortRowsStepPid, sortRowsStepname, (StepMetaInterface)srm);
        transMeta.addStep(sampleRowsStep);

        hi = new TransHopMeta(csvInputStep, sampleRowsStep);
        transMeta.addTransHop(hi);


        //
        // Create a dummy step 1
        //
        String dummyStepname1 = "dummy step 1";
        DummyTransMeta dm1 = new DummyTransMeta();

        String dummyPid1 = registry.getPluginId(StepPluginType.class, dm1);
        StepMeta dummyStep1 = new StepMeta(dummyPid1, dummyStepname1, dm1);
        transMeta.addStep(dummyStep1);

        TransHopMeta hi1 = new TransHopMeta(sampleRowsStep, dummyStep1);
        transMeta.addTransHop(hi1);

        // Now execute the transformation...
        Trans trans = new Trans(transMeta);

        trans.prepareExecution(null);

        StepInterface si = trans.getStepInterface(dummyStepname1, 0);
        RowStepCollector dummyRc1 = new RowStepCollector();
        si.addRowListener(dummyRc1);

        RowProducer rp = trans.addRowProducer(injectorStepname, 0);
        trans.startThreads();

        // add rows
        List<RowMetaAndData> inputList = createData(cim.getFileName()[0]);
        Iterator<RowMetaAndData> it = inputList.iterator();
        while (it.hasNext()) {
            RowMetaAndData rm = it.next();
            rp.putRow(rm.getRowMeta(), rm.getData());
        }
        rp.finished();

        trans.waitUntilFinished();

        // Compare the results
        List<RowMetaAndData> resultRows = dummyRc1.getRowsWritten();
        //List<RowMetaAndData> goldenImageRows = createResultData1();

        return resultRows;
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
