package org.flowframe.etl.pentaho.server.plugins.core.model.json;


import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class TextFileInputMetaSerializer extends JsonSerializer<Object>
{
    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        //super.serialize(textFileInputMeta,jsonGenerator,serializerProvider);
        //jsonGenerator.wri
        value.toString();
    }
}
