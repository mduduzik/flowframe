package org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput;

import flexjson.JSONSerializer;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.provider.local.LocalFile;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.repository.db.etl.trans.util.TransPreviewFactory;
import org.flowframe.etl.pentaho.repository.db.repository.RepositoryUtil;
import org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.BaseDialogDelegateResource;
import org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput.dto.CsvInputMetaDTO;
import org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.dto.TextFileInputFieldDTO;
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
import org.pentaho.di.trans.steps.csvinput.CsvInput;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;
import org.pentaho.di.trans.steps.dummytrans.DummyTransMeta;
import org.pentaho.di.trans.steps.injector.InjectorMeta;
import org.pentaho.di.trans.steps.textfileinput.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/csvinput")
public class CSVInputDialogDelegateResource extends BaseDialogDelegateResource {
    private static Class<?> PKG = CsvInput.class;
    private static PluginRegistry registry = PluginRegistry.getInstance();
    private TextFileInputField[] cachedInputFields;


    @Path("/onnew")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String onNew(@HeaderParam("userid") String userid) throws JSONException {
        //-- Return copy of actual metadata on startup
        CsvInputMetaDTO dto = new CsvInputMetaDTO();
        String json = dto.toJSON();
        JSONObject data = new JSONObject(json);
        JSONObject record = new JSONObject();
        record.put("success", true);
        record.put("data", data);
        return record.toString();
    }

    @Path("/onedit")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String onEdit(@HeaderParam("userid") String userid, @QueryParam("pathId") String pathId) throws Exception {
        CsvInputMeta res = (CsvInputMeta) RepositoryUtil.getStep(repository, pathId).getStepMetaInterface();

        //-- Return copy of actual metadata on startup
        CsvInputMetaDTO dto = new CsvInputMetaDTO(res);
        FileEntry fe = ecmService.getFileEntryById(res.getFilename());
        dto.setFilename(fe.getName());
        dto.setFileEntryId(fe.getFileEntryId()+"");

        return dto.toJSON();
    }


    @Path("/ongetmetadata")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onGetMetadata(@HeaderParam("userid") String userid, CsvInputMetaDTO metaDTO_) throws Exception {
        //Generate metadata
        CsvInputMeta meta_ = (CsvInputMeta) metaDTO_.fromDTO(CsvInputMeta.class);
        meta_.setFilename(metaDTO_.getFileEntryId());
        CsvInputMeta updatedMetadata = updateMetadata(meta_);

        TextFileInputFieldDTO[] fields = TextFileInputFieldDTO.toDTOArray(updatedMetadata.getInputFields());
        JSONSerializer serializer = new JSONSerializer();
        JSONArray rows = new JSONArray(serializer.exclude("class").serialize(fields));

        JSONObject res = new JSONObject();
        res.put("results", fields.length);
        res.put("rows", rows);


        return res.toString();
    }

    @Path("/previewdata")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onPreviewData(@HeaderParam("userid") String userid, CsvInputMetaDTO metaDTO_) throws Exception {
        //Generate metadata
        CsvInputMeta meta_ = (CsvInputMeta) metaDTO_.fromDTO(CsvInputMeta.class);
        meta_.setFilename(metaDTO_.getFileEntryId());
        String json = previewData(meta_);

        return json;

/*        TextFileInputFieldDTO[] fields = TextFileInputFieldDTO.toDTOArray(updatedMetadata.getInputFields());
        JSONSerializer serializer = new JSONSerializer();
        JSONArray rows =  new JSONArray(serializer.serialize(fields));

        JSONObject res = new JSONObject();
        res.put("results",fields.length);
        res.put("rows",rows);


        return res.toString();*/
    }

    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onAdd(@HeaderParam("userid") String userid,
                        CsvInputMetaDTO metaDTO_) throws Exception {
        CsvInputMeta meta_ = (CsvInputMeta) metaDTO_.fromDTO(CsvInputMeta.class);
        meta_.setFilename(metaDTO_.getFileEntryId());
        String csvInputPid = registry.getPluginId(StepPluginType.class, meta_);
        StepMeta csvInputStep = new StepMeta(csvInputPid, metaDTO_.getName(), meta_);
        String pathID = RepositoryUtil.addStep(repository,metaDTO_.getSubDirObjId(),csvInputStep);

        meta_ = (CsvInputMeta)csvInputStep.getStepMetaInterface();
        meta_.setFilename(metaDTO_.getFileEntryId());

        CsvInputMetaDTO dto = new CsvInputMetaDTO(meta_);
        dto.setPathId(pathID);

        return dto.toJSON();
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
                                     FormDataMultiPart mpFileUpload) throws Exception {

        //Get file handle
        FormDataBodyPart fileBP = getFileBodyPart("ext-gen", mpFileUpload);
        String fileName = fileBP.getContentDisposition().getFileName();
        Long fileSize = Long.valueOf(fileSizeStr);
        String mimeType = fileBP.getContentDisposition().getType();
        InputStream sampleCSVInputStream = fileBP.getValueAs(InputStream.class);

        //Save sample to ECM
        FileEntry fe = addOrUpdateSampleFile(sampleCSVInputStream, fileName, mimeType);

        return Response.status(200).entity("{fileentryid: " + fe.getFileEntryId() + ",progress:" + fileSize + ",success:true,filelocation:" + Long.toString(fe.getFileEntryId()) + "}").build();

    }

    @Path("/uploadprogress")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadProgress() throws JSONException {
        JSONObject progressJSon = new JSONObject();
        if (context.getAttribute("fileName") != null) {
            Object bytes_ = context.getAttribute("bytesProcessed");
            Object fileName_ = context.getAttribute("fileName");
            Object fileSize_ = context.getAttribute("fileSize");
            if (fileName_ != null) {
                progressJSon.put("bytesTotal", fileSize_);
                progressJSon.put("bytesUploaded", bytes_);
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


        progressJSon.put("success", true);

        return progressJSon.toString();
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
    public CsvInputMeta updateMetadata(CsvInputMeta inputMetadata) throws Exception {
        FileObject fileObject = null;
        InputStreamReader reader = null;
        CsvInputMeta outputMetadata = null;
        //CsvInputMeta cachedMetadata;
        try {
            LogChannelInterface log = null;
            outputMetadata = (CsvInputMeta) new CsvInputMetaDTO(inputMetadata).fromDTO(CsvInputMeta.class);


            //Get file object
            String samplefileEntryId = inputMetadata.getFilename();
            FileEntry fe = ecmService.getFileEntryById(samplefileEntryId);
            InputStream in = ecmService.getFileAsStream(samplefileEntryId, null);
            fileObject = writeSampleFileToVFSTemp(in, fe.getName() + ".wip");//KettleVFS.getFileObject(sampleFile.getAbsolutePath());
            if (!(fileObject instanceof LocalFile)) {
                // We can only use NIO on local files at the moment, so that's what we limit ourselves to.
                //
                throw new KettleException(BaseMessages.getString(PKG, "CsvInput.Log.OnlyLocalFilesAreSupported"));
            }


            TransMeta transMeta = new TransMeta();
            transMeta.setName("CsvInputTrans");
            log = new LogChannel("CSVInputStepComponentImpl[]");

            String delimiter = inputMetadata.getDelimiter();


            //wFields.table.removeAll();

            InputStream inputStream = KettleVFS.getInputStream(fileObject);


            if (Const.isEmpty(inputMetadata.getEncoding())) {
                reader = new InputStreamReader(inputStream);
            } else {
                reader = new InputStreamReader(inputStream, inputMetadata.getEncoding());
            }

            EncodingType encodingType = EncodingType.guessEncodingType(reader.getEncoding());

            // Read a line of data to determine the number of rows...
            //
            String line = TextFileInput.getLine(log, reader, encodingType, TextFileInputMeta.FILE_FORMAT_MIXED, new StringBuilder(1000));

            // Split the string, header or data into parts...
            //
            String enclosure = inputMetadata.getEnclosure();
            String escapeCharacter = inputMetadata.getEscapeCharacter();
            String[] fieldNames = CsvInput.guessStringsFromLine(log, line, delimiter, enclosure, escapeCharacter);

            Boolean headerPresent = inputMetadata.isHeaderPresent();
            if (!headerPresent) {
                // Don't use field names from the header...
                // Generate field names F1 ... F10
                //
                DecimalFormat df = new DecimalFormat("000"); // $NON-NLS-1$
                for (int i = 0; i < fieldNames.length; i++) {
                    fieldNames[i] = "Field_" + df.format(i); // $NON-NLS-1$
                }
            } else {
                if (!Const.isEmpty(enclosure)) {
                    for (int i = 0; i < fieldNames.length; i++) {
                        if (fieldNames[i].startsWith(enclosure) && fieldNames[i].endsWith(enclosure) && fieldNames[i].length() > 1)
                            fieldNames[i] = fieldNames[i].substring(1, fieldNames[i].length() - 1);
                    }
                }
            }

            // Clean-up UI metadata: Trim the names to make sure...
            inputMetadata.setInputFields(new TextFileInputField[fieldNames.length]);
            for (int i = 0; i < fieldNames.length; i++) {
                fieldNames[i] = Const.trim(fieldNames[i]);
                TextFileInputField ifm = new TextFileInputField();
                ifm.setName(fieldNames[i]);
                ifm.setType(ValueMetaInterface.TYPE_STRING);
                inputMetadata.getInputFields()[i] = ifm;
            }

            int samples = 100;
            if (samples >= 0) {
                //Update cached metadata; fields etc. (before a detailed file analysis) from UI metadata
                updateMetadata(outputMetadata, inputMetadata);

                TextFileCSVAnalyzer analyzer = new TextFileCSVAnalyzer(outputMetadata, transMeta, reader, samples, true);
                String message = analyzer.analyze();
                if (message != null) {
                    //wFields.removeAll();

                    // OK, what's the result of our search?
    /*                    getData(meta, false);
                        wFields.removeEmptyRows();
                        wFields.setRowNums();
                        wFields.optWidth(true);
    
                        EnterTextDialog etd = new EnterTextDialog(shell, BaseMessages.getString(PKG, "CsvInputDialog.ScanResults.DialogTitle"), BaseMessages.getString(PKG, "CsvInputDialog.ScanResults.DialogMessage"), message, true);
                        etd.setReadOnly();
                        etd.open();*/
                }
            }
        } finally {
            if (reader != null)
                reader.close();
            if (fileObject != null)
                fileObject.delete();
        }

        return outputMetadata;
    }

    private void updateMetadata(CsvInputMeta meta, CsvInputMeta inputMetadata) {
        //if (isReceivingInput) {
        meta.setFilenameField(inputMetadata.getFilenameField());
        meta.setIncludingFilename(inputMetadata.isIncludingFilename());
        //} else {
        //    meta.setFilename(inputMetadata.getFilename());
        //}

        meta.setDelimiter(inputMetadata.getDelimiter());
        meta.setEnclosure(inputMetadata.getEnclosure());
        meta.setBufferSize(inputMetadata.getBufferSize());
        meta.setLazyConversionActive(inputMetadata.isLazyConversionActive());
        meta.setHeaderPresent(inputMetadata.isHeaderPresent());
        meta.setRowNumField(inputMetadata.getRowNumField());
        meta.setAddResultFile(inputMetadata.isAddResultFile());
        meta.setRunningInParallel(inputMetadata.isRunningInParallel());
        meta.setNewlinePossibleInFields(inputMetadata.isNewlinePossibleInFields());
        meta.setEncoding(inputMetadata.getEncoding());

        int nrNonEmptyFields = inputMetadata.getInputFields().length;
        meta.allocate(nrNonEmptyFields);

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


    public String previewData(CsvInputMeta inputMetadata) throws Exception {
        FileObject fileObject = null;
        InputStreamReader reader = null;
        CsvInputMeta outputMetadata = null;
        JSONObject res = null;
        File file = null;
        //CsvInputMeta cachedMetadata;
        try {
            LogChannelInterface log = null;
            outputMetadata = (CsvInputMeta) new CsvInputMetaDTO(inputMetadata).fromDTO(CsvInputMeta.class);


            //Get file object
            String samplefileEntryId = inputMetadata.getFilename();
            FileEntry fe = ecmService.getFileEntryById(samplefileEntryId);
            InputStream in = ecmService.getFileAsStream(samplefileEntryId, null);
            file = writeSampleStreamToTempFile(in, fe.getName() + ".wip");
/*
            fileObject = writeSampleFileToVFSTemp(in,fe.getName()+".wip");//KettleVFS.getFileObject(sampleFile.getAbsolutePath());
            if (!(fileObject instanceof LocalFile)) {
                // We can only use NIO on local files at the moment, so that's what we limit ourselves to.
                //
                throw new KettleException(BaseMessages.getString(PKG, "CsvInput.Log.OnlyLocalFilesAreSupported"));
            }

            InputStream inputStream = KettleVFS.getInputStream(fileObject);


            if (Const.isEmpty(inputMetadata.getEncoding())) {
                reader = new InputStreamReader(inputStream);
            } else {
                reader = new InputStreamReader(inputStream, inputMetadata.getEncoding());
            }

            TextFileCSVPreviewer previewer = new TextFileCSVPreviewer(inputMetadata, reader);
            previewer.preview();



            List<Object[]> prevrows = previewer.getPreviewRows("CsvInputPreview");
            RowMetaInterface prevmeta = previewer.getPreviewRowsMeta("CsvInputPreview");
*/

            /**
             *
             * generate rows and metadata
             *
             */
            JSONObject jsonRow;
            Object elm;
            ValueMetaInterface vm;
            Object[] row;
            RowMetaInterface rowMI;
            String obj;
            List<RowMetaAndData> dataAndMetaRows = generatePreviewDataFromFile(inputMetadata, "CsvInputPreview", file.getAbsolutePath());

            //-- metadata
            RowMetaAndData rowMeta = dataAndMetaRows.get(0);
            rowMeta.getRowMeta().removeValueMeta("filename");
            JSONArray metadata = new JSONArray();
            for (int i = 0; i < rowMeta.getRowMeta().getFieldNames().length; i++) {
                vm = rowMeta.getValueMeta(i);
                metadata.put(vm.getName());
            }

            //-- data
            JSONArray rows = new JSONArray();
            int stepCount = 1;
            int limit = 100;
            for (RowMetaAndData prevrow : dataAndMetaRows) {
                jsonRow = new JSONObject();
                row = prevrow.getData();
                rowMI = prevrow.getRowMeta();
                for (int i = 0; i < rowMI.getFieldNames().length; i++) {
                    elm = row[i];
                    vm = rowMI.getValueMeta(i);
                    obj = rowMI.getString(row, i);
                    if (!vm.getName().equals("filename"))
                        jsonRow.put(vm.getName(), obj);
                }
                rows.put(jsonRow);
                stepCount++;
                if (stepCount > limit)
                    break;
            }


            res = new JSONObject();
            JSONObject metaDataWrapper = new JSONObject();
            metaDataWrapper.put("fields",metadata);
            metaDataWrapper.put("totalProperty","results");
            metaDataWrapper.put("root","rows");
            res.put("results", rows.length());
            res.put("rows", rows);
            res.put("metaData", metaDataWrapper);

        } finally {
            if (file != null)
                file.delete();
        }

        return res.toString();
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
    public class TextFileCSVAnalyzer {
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
        public TextFileCSVAnalyzer(InputFileMetaInterface meta, TransMeta transMeta, InputStreamReader reader, int samples, boolean replaceMeta) {
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


        private String analyze() throws KettleException {
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
                for (int i = 0; i < convertRowMeta.size(); i++)
                    convertRowMeta.getValueMeta(i).setType(ValueMetaInterface.TYPE_STRING);

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

                for (int i = 0; i < nrfields; i++) {
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
                    for (int j = 0; j < Const.getDateFormats().length; j++) {
                        dateFormat[i][j] = true;
                        minDate[i][j] = Const.MAX_DATE;
                        maxDate[i][j] = Const.MIN_DATE;
                    }
                    dateFormatCount[i] = Const.getDateFormats().length;

                    // Init number guess
                    isNumber[i] = true;
                    for (int j = 0; j < Const.getNumberFormats().length; j++) {
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
                int skipped = 1;

                if (meta.hasHeader()) {

                    while (line != null && skipped < meta.getNrHeaderLines()) {
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
                while (!errorFound && line != null && (linenr <= samples || samples == 0)) {
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

                    if (r == null) {
                        errorFound = true;
                        continue;
                    }
                    rownumber++;
                    for (int i = 0; i < nrfields && i < r.length; i++) {
                        StringEvaluator evaluator;
                        if (i >= evaluators.size()) {
                            evaluator = new StringEvaluator(true);
                            evaluators.add(evaluator);
                        } else {
                            evaluator = evaluators.get(i);
                        }

                        String string = rowMeta.getString(r, i);

                        if (i == 0) {
                            System.out.println();
                        }
                        evaluator.evaluateString(string);
                    }

                    fileLineNumber++;
                    if (r != null) {
                        linenr++;
                    }

                    // Grab another line...
                    //
                    line = TextFileInput.getLine(log, reader, encodingType, fileFormatType, lineBuffer);
                }

                message = new StringBuilder();
                message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.ResultAfterScanning", "" + (linenr - 1)));
                message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.HorizontalLine"));

                for (int i = 0; i < nrfields; i++) {
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
                    if (result != null) {
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


                    message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.FieldNumber", "" + (i + 1)));

                    message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.FieldName", field.getName()));
                    message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.FieldType", field.getTypeDesc()));

                    switch (field.getType()) {
                        case ValueMetaInterface.TYPE_NUMBER:
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.EstimatedLength", (field.getLength() < 0 ? "-" : "" + field.getLength())));
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.EstimatedPrecision", field.getPrecision() < 0 ? "-" : "" + field.getPrecision()));
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.NumberFormat", field.getFormat()));

                            if (!evaluationResults.isEmpty()) {
                                if (evaluationResults.size() > 1) {
                                    message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.WarnNumberFormat"));
                                }

                                for (StringEvaluationResult seResult : evaluationResults) {
                                    String mask = seResult.getConversionMeta().getConversionMask();

                                    message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.NumberFormat2", mask));
                                    message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.TrimType", seResult.getConversionMeta().getTrimType()));
                                    message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.NumberMinValue", seResult.getMin()));
                                    message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.NumberMaxValue", seResult.getMax()));

                                    try {
                                        df2.applyPattern(mask);
                                        df2.setDecimalFormatSymbols(dfs2);
                                        double mn = df2.parse(seResult.getMin().toString()).doubleValue();
                                        message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.NumberExample", mask, seResult.getMin(), Double.toString(mn)));
                                    } catch (Exception e) {
                                        if (log.isDetailed())
                                            log.logDetailed("This is unexpected: parsing [" + seResult.getMin() + "] with format [" + mask + "] did not work.");
                                    }
                                }
                            }
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.NumberNrNullValues", "" + nrnull[i]));
                            break;
                        case ValueMetaInterface.TYPE_STRING:
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.StringMaxLength", "" + field.getLength()));
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.StringMinValue", minstr[i]));
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.StringMaxValue", maxstr[i]));
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.StringNrNullValues", "" + nrnull[i]));
                            break;
                        case ValueMetaInterface.TYPE_DATE:
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.DateMaxLength", field.getLength() < 0 ? "-" : "" + field.getLength()));
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.DateFormat", field.getFormat()));
                            if (dateFormatCount[i] > 1) {
                                message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.WarnDateFormat"));
                            }
                            if (!Const.isEmpty(minstr[i])) {
                                for (int x = 0; x < Const.getDateFormats().length; x++) {
                                    if (dateFormat[i][x]) {
                                        message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.DateFormat2", Const.getDateFormats()[x]));
                                        Date mindate = minDate[i][x];
                                        Date maxdate = maxDate[i][x];
                                        message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.DateMinValue", mindate.toString()));
                                        message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.DateMaxValue", maxdate.toString()));

                                        daf2.applyPattern(Const.getDateFormats()[x]);
                                        try {
                                            Date md = daf2.parse(minstr[i]);
                                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.DateExample", Const.getDateFormats()[x], minstr[i], md.toString()));
                                        } catch (Exception e) {
                                            if (log.isDetailed())
                                                log.logDetailed("This is unexpected: parsing [" + minstr[i] + "] with format [" + Const.getDateFormats()[x] + "] did not work.");
                                        }
                                    }
                                }
                            }
                            message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.DateNrNullValues", "" + nrnull[i]));
                            break;
                        default:
                            break;
                    }
                    if (nrnull[i] == linenr - 1) {
                        message.append(BaseMessages.getString(PKG, "CSVInputDialogDelegateResource.Info.AllNullValues"));
                    }
                    message.append(Const.CR);

                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Error e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return message.toString();
        }
    }

    /**
     * CSV Data Previewer
     */
    public class TextFileCSVPreviewer {
        private Class<?> PKG = TextFileInputMeta.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

        private InputFileMetaInterface meta;

        private final String[] previewStepNames;

        private final int[] previewSize;

        private InputStreamReader reader;

        private TransMeta transMeta;

        private LogChannelInterface log;

        private EncodingType encodingType;

        private String csvInputPreview = "CsvInputPreview";
        private TransDebugMeta transDebugMeta;
        private String loggingText;
        private Trans trans;

        /**
         * Creates a new dialog that will handle the wait while we're finding out what tables, views etc we can reach in the
         * database.
         */
        public TextFileCSVPreviewer(CsvInputMeta inputMeta, InputStreamReader sampleFileReader) {
            this.meta = inputMeta;
            this.reader = sampleFileReader;
            this.transMeta = new TransMeta();
            this.previewStepNames = new String[]{"CsvInputPreview", "dummy"};
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


    public static final List<RowMetaAndData> generatePreviewDataFromFile(CsvInputMeta cim, String oneStepname, String inputFilename) throws KettleException {
        KettleEnvironment.init();

        cim.setFilename(inputFilename);
        cim.setFilenameField("filename");
        cim.setIncludingFilename(true);

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
        // Create a dummy step 1
        //
        String dummyStepname1 = "dummy step 1";
        DummyTransMeta dm1 = new DummyTransMeta();

        String dummyPid1 = registry.getPluginId(StepPluginType.class, dm1);
        StepMeta dummyStep1 = new StepMeta(dummyPid1, dummyStepname1, dm1);
        transMeta.addStep(dummyStep1);

        TransHopMeta hi1 = new TransHopMeta(csvInputStep, dummyStep1);
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
        List<RowMetaAndData> inputList = createData(inputFilename);
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
