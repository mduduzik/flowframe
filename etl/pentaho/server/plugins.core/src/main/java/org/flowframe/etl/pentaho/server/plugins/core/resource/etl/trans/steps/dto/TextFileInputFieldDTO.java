package org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.dto;

import flexjson.JSONSerializer;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextFileInputFieldDTO extends BaseDTO {
    private String 	name;
    private int 	position;
    private int 	length;
    private int 	type;
    private String  typeDescription;
    private boolean ignore;
    private String 	format;
    private int 	trimtype;
    private int 	precision;
    private String 	currencySymbol;
    private String 	decimalSymbol;
    private String 	groupSymbol;
    private boolean repeat;
    private String 	nullString;
    private String  ifNullValue;



    public TextFileInputFieldDTO() {
    }

    public TextFileInputFieldDTO(TextFileInputField field) {
        setName(field.getName());
        setPosition(field.getPosition());
        setLength(field.getLength());
        setType(field.getType());
        setTypeDescription(ValueMeta.getTypeDesc(field.getType()));
        setIgnore(field.isIgnored());
        setFormat(field.getFormat());
        setTrimtype(field.getTrimType());
        setPrecision(field.getPrecision());
        setCurrencySymbol(field.getCurrencySymbol());
        setDecimalSymbol(field.getDecimalSymbol());
        setRepeat(field.isRepeated());
        setNullString(field.getNullString());
        setIfNullValue(field.getIfNullValue());
    }

    public TextFileInputFieldDTO(String name, int position, int length, int type, boolean ignore, String format, int trimtype, int precision, String currencySymbol, String decimalSymbol, String groupSymbol, boolean repeat, String nullString, String ifNullValue) {
        this.name = name;
        this.position = position;
        this.length = length;
        this.type = type;
        this.ignore = ignore;
        this.format = format;
        this.trimtype = trimtype;
        this.precision = precision;
        this.currencySymbol = currencySymbol;
        this.decimalSymbol = decimalSymbol;
        this.groupSymbol = groupSymbol;
        this.repeat = repeat;
        this.nullString = nullString;
        this.ifNullValue = ifNullValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getTrimtype() {
        return trimtype;
    }

    public void setTrimtype(int trimtype) {
        this.trimtype = trimtype;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getDecimalSymbol() {
        return decimalSymbol;
    }

    public void setDecimalSymbol(String decimalSymbol) {
        this.decimalSymbol = decimalSymbol;
    }

    public String getGroupSymbol() {
        return groupSymbol;
    }

    public void setGroupSymbol(String groupSymbol) {
        this.groupSymbol = groupSymbol;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public String getNullString() {
        return nullString;
    }

    public void setNullString(String nullString) {
        this.nullString = nullString;
    }

    public String getIfNullValue() {
        return ifNullValue;
    }

    public void setIfNullValue(String ifNullValue) {
        this.ifNullValue = ifNullValue;
    }

    public String toJSON() {
        JSONSerializer serializer = new JSONSerializer();
        return serializer.serialize(this);
    }

    static public TextFileInputFieldDTO[] toDTOArray(TextFileInputField[] fields) {
         List<TextFileInputFieldDTO> dtos = new ArrayList<TextFileInputFieldDTO>();
        for (TextFileInputField field : fields) {
            dtos.add(new TextFileInputFieldDTO(field));
        }

        return dtos.toArray(new TextFileInputFieldDTO[]{});
    }

}
