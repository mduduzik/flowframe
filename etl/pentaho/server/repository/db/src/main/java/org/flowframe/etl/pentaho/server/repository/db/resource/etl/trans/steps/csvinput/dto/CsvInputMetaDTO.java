package org.flowframe.etl.pentaho.server.repository.db.resource.etl.trans.steps.csvinput.dto;

import flexjson.JSONDeserializer;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.repository.db.resource.etl.trans.steps.dto.BaseDTO;
import org.flowframe.etl.pentaho.server.repository.db.resource.etl.trans.steps.dto.TextFileInputFieldDTO;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class CsvInputMetaDTO extends BaseDTO {
    private String name;

    private String filename;

    private String filenameField;

    private String fileEntryId;

    private boolean includingFilename;

    private String rowNumField;

    private boolean headerPresent = true;

    private String delimiter = ",";
    private String enclosure = "\"";

    private String bufferSize = "50000";

    private boolean lazyConversionActive = true;

    private List<TextFileInputFieldDTO> inputFields;

    private boolean isaddresult;

    private boolean runningInParallel;

    private String encoding;

    private boolean newlinePossibleInFields;

    public CsvInputMetaDTO() {
    }

    public CsvInputMetaDTO(CsvInputMeta csvInput, String sampleFileEntryId) {
        this(csvInput);
        this.fileEntryId = sampleFileEntryId;
    }

    public CsvInputMetaDTO(CsvInputMeta csvInput) {
        setName(csvInput.getName());
        setFilename(csvInput.getFilename());
        setFilenameField(csvInput.getFilenameField());
        setIncludingFilename(csvInput.isIncludingFilename());
        setRowNumField(csvInput.getRowNumField());
        setHeaderPresent(csvInput.isHeaderPresent());
        setDelimiter(csvInput.getDelimiter());
        setEnclosure(csvInput.getEnclosure());
        setBufferSize(csvInput.getBufferSize());
        setLazyConversionActive(csvInput.isLazyConversionActive());
        TextFileInputField[] fields_ = csvInput.getInputFields();
        inputFields = new ArrayList<TextFileInputFieldDTO>();
        if (fields_ != null) {
            for (TextFileInputField field_ : fields_) {
                inputFields.add(new TextFileInputFieldDTO(field_));
            }
        }
        setIsaddresult(csvInput.isAddResultFile());
        setRunningInParallel(csvInput.isRunningInParallel());
        setEncoding(csvInput.getEncoding());
        setNewlinePossibleInFields(csvInput.isNewlinePossibleInFields());
    }

    public CsvInputMetaDTO(String filename, String filenameField, boolean includingFilename, String rowNumField, boolean headerPresent, String delimiter, String enclosure, String bufferSize, boolean lazyConversionActive, List<TextFileInputFieldDTO> inputFields, boolean isaddresult, boolean runningInParallel, String encoding, boolean newlinePossibleInFields) {
        this.filename = filename;
        this.filenameField = filenameField;
        this.includingFilename = includingFilename;
        this.rowNumField = rowNumField;
        this.headerPresent = headerPresent;
        this.delimiter = delimiter;
        this.enclosure = enclosure;
        this.bufferSize = bufferSize;
        this.lazyConversionActive = lazyConversionActive;
        this.inputFields = inputFields;
        this.isaddresult = isaddresult;
        this.runningInParallel = runningInParallel;
        this.encoding = encoding;
        this.newlinePossibleInFields = newlinePossibleInFields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileEntryId() {
        return fileEntryId;
    }

    public void setFileEntryId(String fileEntryId) {
        this.fileEntryId = fileEntryId;
    }

    public String getFilenameField() {
        return filenameField;
    }

    public void setFilenameField(String filenameField) {
        this.filenameField = filenameField;
    }

    public boolean isIncludingFilename() {
        return includingFilename;
    }

    public void setIncludingFilename(boolean includingFilename) {
        this.includingFilename = includingFilename;
    }

    public String getRowNumField() {
        return rowNumField;
    }

    public void setRowNumField(String rowNumField) {
        this.rowNumField = rowNumField;
    }

    public boolean isHeaderPresent() {
        return headerPresent;
    }

    public void setHeaderPresent(boolean headerPresent) {
        this.headerPresent = headerPresent;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(String bufferSize) {
        this.bufferSize = bufferSize;
    }

    public boolean isLazyConversionActive() {
        return lazyConversionActive;
    }

    public void setLazyConversionActive(boolean lazyConversionActive) {
        this.lazyConversionActive = lazyConversionActive;
    }

    public List<TextFileInputFieldDTO> getInputFields() {
        return inputFields;
    }

    public void setInputFields(List<TextFileInputFieldDTO> inputFields) {
        this.inputFields = inputFields;
    }

    public boolean isIsaddresult() {
        return isaddresult;
    }

    public void setIsaddresult(boolean isaddresult) {
        this.isaddresult = isaddresult;
    }

    public boolean isRunningInParallel() {
        return runningInParallel;
    }

    public void setRunningInParallel(boolean runningInParallel) {
        this.runningInParallel = runningInParallel;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isNewlinePossibleInFields() {
        return newlinePossibleInFields;
    }

    public void setNewlinePossibleInFields(boolean newlinePossibleInFields) {
        this.newlinePossibleInFields = newlinePossibleInFields;
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
        final CsvInputMeta meta = (CsvInputMeta)metaDeserializer.deserialize(obj.toString(),type);
        if (fieldList != null) {
            meta.setInputFields(fieldList.toArray(new TextFileInputField[]{}));
        }
        return meta;
    }
}
