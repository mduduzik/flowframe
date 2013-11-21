package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.io.metadata;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.pentaho.di.trans.steps.excelinput.ExcelInputField;

import java.io.IOException;

/**
 * Created by Mduduzi on 11/7/13.
 */

public class ExcelInputFieldSerializer extends JsonSerializer<ExcelInputField>
{

    @Override
    public void serialize(ExcelInputField value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        try {
            jgen.writeStartObject();
            jgen.writeStringField("name",value.getName());
            jgen.writeNumberField("type",value.getType());
            jgen.writeStringField("typeDesc",value.getTypeDesc());
            jgen.writeNumberField("length",value.getLength());
            jgen.writeNumberField("precision",value.getPrecision());
            jgen.writeNumberField("trimtype",value.getTrimType());
            jgen.writeStringField("trimTypeDesc",value.getTrimTypeDesc());
            jgen.writeStringField("format",value.getFormat());
            jgen.writeStringField("currencySymbol",value.getCurrencySymbol());
            jgen.writeStringField("decimalSymbol",value.getDecimalSymbol());
            jgen.writeStringField("groupSymbol",value.getGroupSymbol());
            jgen.writeBooleanField("repeat",value.isRepeated());
            jgen.writeEndObject();

        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}