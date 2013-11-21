package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.io.metadata;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.pentaho.di.core.row.ValueMetaInterface;

import java.io.IOException;

/**
 * Created by Mduduzi on 11/7/13.
 */

public class ValueMetaInterfaceSerializer extends JsonSerializer<ValueMetaInterface>
{

    @Override
    public void serialize(ValueMetaInterface value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        try {
            jgen.writeStartObject();
            jgen.writeStringField("name",value.getName());
            jgen.writeStringField("typeDesc",value.getTypeDesc());
            jgen.writeNumberField("type",value.getType());
            jgen.writeEndObject();

        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}