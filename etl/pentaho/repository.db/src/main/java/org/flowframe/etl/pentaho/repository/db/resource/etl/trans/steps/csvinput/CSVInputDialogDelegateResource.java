package org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.provider.local.LocalFile;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.BaseDialogDelegateResource;
import org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput.dto.CsvInputMetaDTO;
import org.flowframe.kernel.common.utils.Validator;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleFileException;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.util.StringEvaluationResult;
import org.pentaho.di.core.util.StringEvaluator;
import org.pentaho.di.core.vfs.KettleVFS;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.steps.csvinput.CsvInput;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;
import org.pentaho.di.trans.steps.textfileinput.*;
import org.pentaho.di.ui.core.dialog.EnterNumberDialog;
import org.pentaho.di.ui.core.dialog.EnterTextDialog;
import org.pentaho.di.ui.trans.steps.textfileinput.TextFileCSVImportProgressDialog;

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
    private TextFileInputField[] cachedInputFields;

    @Context
    public void setServletContext(ServletContext context) throws ServletException {
        super.setServletContext(context);


        //-- WorkDir
        File workDir = new File(super.tmpDir, "NewCSVInputDialogResourceDir");
        if (!workDir.exists())
            if (!workDir.mkdirs()) {
                throw new ServletException("Unable to create classes temporary directory");
            }
        setSessionAttribute(ATTRIBUTENAME_WORKDIR,workDir);
    }


    @Path("/onstart")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String onStart() throws JSONException {
        init();

        CsvInputMetaDTO dto = new CsvInputMetaDTO((CsvInputMeta)getCachedMetadata());
        return dto.toJSON();
    }


    @Path("/ongetmetadata")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String onGetMetadata(CsvInputMetaDTO meta_) throws JSONException, KettleException, FileSystemException, UnsupportedEncodingException {
        //Input from client
        CsvInputMeta meta = (CsvInputMeta) meta_.fromDTO(CsvInputMeta.class);
        cacheMetadata(meta);

        //Get Meta


        CsvInputMetaDTO dto = new CsvInputMetaDTO((CsvInputMeta)getCachedMetadata());
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
                                     FormDataMultiPart mpFileUpload) throws InterruptedException, IOException {

        FormDataBodyPart fileBP = getFileBodyPart("ext-gen", mpFileUpload);
        String fileName = fileBP.getContentDisposition().getFileName();
        Long fileSize = Long.valueOf(fileSizeStr);
        InputStream sampleCSVInputStream = fileBP.getValueAs(InputStream.class);

        context.setAttribute("sampleCSVFileName",fileName);
        context.setAttribute("fileSize",fileSize);

        //Save sample to temp
        File sampleFile = writeSampleStreamToFile(sampleCSVInputStream, fileName);

        //Update metadata
        CsvInputMeta meta = (CsvInputMeta) getCachedMetadata();
        meta.setFilename(sampleFile.getAbsolutePath());

        return Response.status(200).entity("{progress:" + fileSize + ",success:true,filelocation:" + sampleFile.getAbsolutePath() + "}").build();

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




    @Override
    public void init() {
        CsvInputMeta inputMeta  = new CsvInputMeta();
        super.cacheMetadata(inputMeta);
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

    //File Analysis
    public CsvInputMeta generateMetadata(CsvInputMeta meta, InputStream sampleDataInputStream) throws Exception {
        LogChannelInterface log = null;

        try {
            TransMeta transMeta = new TransMeta();
            transMeta.setName("CsvInputTrans");
            log = new LogChannel("CSVInputStepComponentImpl[]");

            File sampleFile = (File)getSessionAttribute(ATTRIBUTENAME_SAMPLEFILE);

            String delimiter = meta.getDelimiter();

            FileObject fileObject = KettleVFS.getFileObject(sampleFile.getAbsolutePath());
            if (!(fileObject instanceof LocalFile)) {
                // We can only use NIO on local files at the moment, so that's what we limit ourselves to.
                //
                throw new KettleException(BaseMessages.getString(PKG, "CsvInput.Log.OnlyLocalFilesAreSupported"));
            }

            //wFields.table.removeAll();

            InputStream inputStream = KettleVFS.getInputStream(fileObject);

            InputStreamReader reader;
            if (Const.isEmpty(meta.getEncoding())) {
                reader = new InputStreamReader(inputStream);
            } else {
                reader = new InputStreamReader(inputStream, meta.getEncoding());
            }

            EncodingType encodingType = EncodingType.guessEncodingType(reader.getEncoding());

            // Read a line of data to determine the number of rows...
            //
            String line = TextFileInput.getLine(log, reader, encodingType, TextFileInputMeta.FILE_FORMAT_MIXED, new StringBuilder(1000));

            // Split the string, header or data into parts...
            //
            String enclosure = meta.getEnclosure();
            String escapeCharacter = meta.getEscapeCharacter();
            String[] fieldNames = CsvInput.guessStringsFromLine(log, line, delimiter, enclosure, escapeCharacter);

            Boolean headerPresent = meta.isHeaderPresent();
            if (!headerPresent) {
                // Don't use field names from the header...
                // Generate field names F1 ... F10
                //
                DecimalFormat df = new DecimalFormat("000"); // $NON-NLS-1$
                for (int i=0;i<fieldNames.length;i++) {
                    fieldNames[i] = "Field_"+df.format(i); // $NON-NLS-1$
                }
            }
            else
            {
                if (!Const.isEmpty(enclosure)) {
                    for (int i=0;i<fieldNames.length;i++) {
                        if (fieldNames[i].startsWith(enclosure) && fieldNames[i].endsWith(enclosure) && fieldNames[i].length()>1) fieldNames[i] = fieldNames[i].substring(1, fieldNames[i].length()-1);
                    }
                }
            }

            // Trim the names to make sure...
            //
            this.cachedInputFields = new TextFileInputField[fieldNames.length];
            for (int i=0;i<fieldNames.length;i++) {
                fieldNames[i] = Const.trim(fieldNames[i]);
                TextFileInputField ifm = new TextFileInputField();
                ifm.setName(fieldNames[i]);
                ifm.setType(ValueMetaInterface.TYPE_STRING);
                this.cachedInputFields[i] = ifm;
            }

            int samples = 100;
            if (samples >= 0)
            {
                TextFileCSVAnalyzer analyzer = new TextFileCSVAnalyzer(meta, transMeta, reader, samples, true);
                String message = analyzer.analyze();
                if (message!=null)
                {
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
        } catch (Exception e) {
            throw e;
        }

        return meta;
    }

    public class TextFileCSVAnalyzer
    {
        private Class<?> PKG = TextFileInputMeta.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

        private InputFileMetaInterface meta;

        private int               samples;

        private boolean           replaceMeta;

        private String            message;

        private String            debug;

        private long              rownumber;

        private InputStreamReader reader;

        private TransMeta         transMeta;

        private LogChannelInterface	log;

        private EncodingType encodingType;

        /**
         * Creates a new dialog that will handle the wait while we're finding out what tables, views etc we can reach in the
         * database.
         */
        public TextFileCSVAnalyzer(InputFileMetaInterface meta, TransMeta transMeta, InputStreamReader reader, int samples, boolean replaceMeta )
        {
            this.meta        = meta;
            this.reader      = reader;
            this.samples     = samples;
            this.replaceMeta = replaceMeta;
            this.transMeta   = transMeta;

            message = null;
            debug = "init";
            rownumber = 1L;

            this.log = new LogChannel(transMeta);

            this.encodingType = EncodingType.guessEncodingType(reader.getEncoding());

        }


        private String analyze() throws KettleException
        {
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
                message.append(BaseMessages.getString(PKG, "TextFileCSVImportProgressDialog.Info.ResultAfterScanning", ""+(linenr-1)));
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Error e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return message.toString();
        }
    }

}
