package org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.textfileinput.dto;

import flexjson.JSONDeserializer;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.plugins.core.model.BaseDTO;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.dto.TextFileInputFieldDTO;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextFileInputMetaDTO extends BaseDTO {
    /** */
    private String name;

    /** */
    private String fileName[];//All protocols in VFS, e.g. http://test%40liferay.com:test@localhost:7080/api/secure/webdav/guest/document_library/Organization-1/sales_data.csv

    /** **/
    private String fileEntryId;

    /** Type of file: CSV or fixed */
    private String fileType;

    /** The number of header lines, defaults to 1 */
    private int nrHeaderLines = 1;

    /** Flag indicating that the file contains one header line that should be skipped. */
    private boolean header = true;

    /** Escape character used to escape the enclosure String (\) */
    private String escapeCharacter = "\\";

    /** String used to enclose separated fields (") */
    private String enclosure = "\"";

    /** Switch to allow breaks (CR/LF) in Enclosures */
    private boolean breakInEnclosureAllowed = false;

    /** String used to separated field (;) */
    private String separator = ",";

    /** Are we accepting filenames in input rows?  */
    private boolean acceptingFilenames = false;

    /** If receiving input rows, should we pass through existing fields? */
    private boolean passingThruFields;

    /** The field in which the filename is placed */
    private String  acceptingField;

    /** The stepname to accept filenames from */
    private String  acceptingStepName;


    /** Flag indicating that the file contains one footer line that should be skipped. */
    private boolean footer = false;

    /** The number of footer lines, defaults to 1 */
    private int nrFooterLines = 0;

    /** Flag indicating that a single line is wrapped onto one or more lines in the text file. */
    private boolean lineWrapped = false;

    /** The number of times the line wrapped */
    private int nrWraps = 0;

    /** Flag indicating that the text-file has a paged layout. */
    private boolean layoutPaged = false;

    /** The number of lines in the document header */
    private int nrLinesDocHeader = 0;

    /** The number of lines to read per page */
    private int nrLinesPerPage = 0;

    /** Type of compression being used */
    private String fileCompression = null;

    /** Flag indicating that we should skip all empty lines */
    private boolean noEmptyLines = true;

    /** Flag indicating that we should include the filename in the output */
    private boolean includeFilename = true;

    /** The name of the field in the output containing the filename */
    private String filenameField;

    /** Flag indicating that a row number field should be included in the output */
    private boolean includeRowNumber = true;

    /** Flag indicating row number is per file */
    private boolean rowNumberByFile = true;

    /** The name of the field in the output containing the row number */
    private String rowNumberField = "RN";

    /** The file format: DOS or UNIX or mixed*/
    private String fileFormat;

    /** The maximum number or lines to read */
    private long rowLimit;

    /** Add result to sub-seq step*/
    private boolean isaddresult;

    private List<TextFileInputFieldDTO> inputFields;

    public TextFileInputMetaDTO() {
        breakInEnclosureAllowed = false;
        header = true;
        nrHeaderLines = 1;
        footer = false;
        nrFooterLines = 1;
        lineWrapped = false;
        nrWraps = 1;
        layoutPaged = false;
        nrLinesPerPage = 80;
        nrLinesDocHeader = 0;
        fileCompression = "None";
        noEmptyLines = true;
        fileFormat = "DOS";
        fileType = "CSV";
        includeFilename = false;
        filenameField = "";
        includeRowNumber = false;
        rowNumberField = "";
        rowNumberByFile = false;

        int nrfiles = 0;
        int nrfields = 0;
        int nrfilters = 0;

        allocate(nrfiles, nrfields, nrfilters);

        rowLimit = 0L;
    }

    public TextFileInputMetaDTO(TextFileInputMeta meta) {
        this();
        setName(meta.getName());
        setFileName(meta.getFileName());

        TextFileInputField[] fields_ = meta.getInputFields();
        inputFields = new ArrayList<TextFileInputFieldDTO>();
        if (fields_ != null) {
            for (TextFileInputField field_ : fields_) {
                inputFields.add(new TextFileInputFieldDTO(field_));
            }
        }
    }

    public void allocate(int nrfiles, int nrfields, int nrfilters)
    {
        fileName = new String[nrfiles];

        inputFields = new ArrayList<TextFileInputFieldDTO>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getFileName() {
        return fileName;
    }

    public void setFileName(String[] fileName) {
        this.fileName = fileName;
    }

    public String getFileEntryId() {
        return fileEntryId;
    }

    public void setFileEntryId(String fileEntryId) {
        this.fileEntryId = fileEntryId;
    }

    public int getNrHeaderLines() {
        return nrHeaderLines;
    }

    public void setNrHeaderLines(int nrHeaderLines) {
        this.nrHeaderLines = nrHeaderLines;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public String getEscapeCharacter() {
        return escapeCharacter;
    }

    public void setEscapeCharacter(String escapeCharacter) {
        this.escapeCharacter = escapeCharacter;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public boolean isBreakInEnclosureAllowed() {
        return breakInEnclosureAllowed;
    }

    public void setBreakInEnclosureAllowed(boolean breakInEnclosureAllowed) {
        this.breakInEnclosureAllowed = breakInEnclosureAllowed;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public boolean isAcceptingFilenames() {
        return acceptingFilenames;
    }

    public void setAcceptingFilenames(boolean acceptingFilenames) {
        this.acceptingFilenames = acceptingFilenames;
    }

    public boolean isPassingThruFields() {
        return passingThruFields;
    }

    public void setPassingThruFields(boolean passingThruFields) {
        this.passingThruFields = passingThruFields;
    }

    public String getAcceptingField() {
        return acceptingField;
    }

    public void setAcceptingField(String acceptingField) {
        this.acceptingField = acceptingField;
    }

    public String getAcceptingStepName() {
        return acceptingStepName;
    }

    public void setAcceptingStepName(String acceptingStepName) {
        this.acceptingStepName = acceptingStepName;
    }

    public boolean isFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public int getNrFooterLines() {
        return nrFooterLines;
    }

    public void setNrFooterLines(int nrFooterLines) {
        this.nrFooterLines = nrFooterLines;
    }

    public boolean isLineWrapped() {
        return lineWrapped;
    }

    public void setLineWrapped(boolean lineWrapped) {
        this.lineWrapped = lineWrapped;
    }

    public int getNrWraps() {
        return nrWraps;
    }

    public void setNrWraps(int nrWraps) {
        this.nrWraps = nrWraps;
    }

    public boolean isLayoutPaged() {
        return layoutPaged;
    }

    public void setLayoutPaged(boolean layoutPaged) {
        this.layoutPaged = layoutPaged;
    }

    public int getNrLinesDocHeader() {
        return nrLinesDocHeader;
    }

    public void setNrLinesDocHeader(int nrLinesDocHeader) {
        this.nrLinesDocHeader = nrLinesDocHeader;
    }

    public int getNrLinesPerPage() {
        return nrLinesPerPage;
    }

    public void setNrLinesPerPage(int nrLinesPerPage) {
        this.nrLinesPerPage = nrLinesPerPage;
    }

    public String getFileCompression() {
        return fileCompression;
    }

    public void setFileCompression(String fileCompression) {
        this.fileCompression = fileCompression;
    }

    public boolean isNoEmptyLines() {
        return noEmptyLines;
    }

    public void setNoEmptyLines(boolean noEmptyLines) {
        this.noEmptyLines = noEmptyLines;
    }

    public boolean isIncludeFilename() {
        return includeFilename;
    }

    public void setIncludeFilename(boolean includeFilename) {
        this.includeFilename = includeFilename;
    }

    public String getFilenameField() {
        return filenameField;
    }

    public void setFilenameField(String filenameField) {
        this.filenameField = filenameField;
    }

    public boolean isIncludeRowNumber() {
        return includeRowNumber;
    }

    public void setIncludeRowNumber(boolean includeRowNumber) {
        this.includeRowNumber = includeRowNumber;
    }

    public boolean isRowNumberByFile() {
        return rowNumberByFile;
    }

    public void setRowNumberByFile(boolean rowNumberByFile) {
        this.rowNumberByFile = rowNumberByFile;
    }

    public String getRowNumberField() {
        return rowNumberField;
    }

    public void setRowNumberField(String rowNumberField) {
        this.rowNumberField = rowNumberField;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public long getRowLimit() {
        return rowLimit;
    }

    public void setRowLimit(long rowLimit) {
        this.rowLimit = rowLimit;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isIsaddresult() {
        return isaddresult;
    }

    public void setIsaddresult(boolean isaddresult) {
        this.isaddresult = isaddresult;
    }

    public List<TextFileInputFieldDTO> getInputFields() {
        return inputFields;
    }

    public void setInputFields(List<TextFileInputFieldDTO> inputFields) {
        this.inputFields = inputFields;
    }

    @Override
    public Object fromDTO(Class type) throws JSONException {
        final String thisJson = toJSON();
        final JSONObject obj = new JSONObject(thisJson);
        obj.remove("class");

        final JSONDeserializer metaDeserializer = new JSONDeserializer();
        JSONArray ifls = null;
        List<TextFileInputField> fieldList = null;
        if (obj.has("inputFields") && obj.get("inputFields") != JSONObject.NULL){
            fieldList = new ArrayList<TextFileInputField>();
            ifls = (JSONArray) obj.get("inputFields");
            for (int i=0;i<ifls.length(); i++){
                ((JSONObject)ifls.get(i)).remove("class");
                fieldList.add((TextFileInputField)metaDeserializer.deserialize(((JSONObject)ifls.get(i)).toString(),TextFileInputField.class));
            }
        }
        obj.remove("inputFields");
        final TextFileInputMeta meta = (TextFileInputMeta)metaDeserializer.deserialize(obj.toString(),type);
        meta.setDateFormatLocale(Locale.getDefault());
        if (fieldList != null) {
            meta.setInputFields(fieldList.toArray(new TextFileInputField[]{}));
        }
        return meta;
    }
}
