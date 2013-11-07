package org.flowframe.etl.pentaho.server.plugins.core.model.json;


import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.KeyDeserializer;

import java.io.IOException;

public class JobEntryCopyKeyDeserializer extends KeyDeserializer
{

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }
}
