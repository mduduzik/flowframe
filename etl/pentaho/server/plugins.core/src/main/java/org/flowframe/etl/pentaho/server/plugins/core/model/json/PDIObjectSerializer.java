package org.flowframe.etl.pentaho.server.plugins.core.model.json;


import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.TreeMap;

public class PDIObjectSerializer<T> extends JsonSerializer<T>
{
    private final ObjectMapper mapper;

    public PDIObjectSerializer(ObjectMapper mapper) {
       this.mapper = mapper;
    }

    @Override
    public void serialize(T value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        //super.serialize(textFileInputMeta,jsonGenerator,serializerProvider);
        //jsonGenerator.wri
        Object o = mapper.convertValue(value, TreeMap.class);
        value.toString();
    }
}
