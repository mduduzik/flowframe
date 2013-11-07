package org.flowframe.etl.pentaho.server.plugins.core.model.json.deserializer;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.type.JavaType;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;

import java.io.IOException;

/**
 * Created by Mduduzi on 11/6/13.
 */


public class GenericBeanDeserializer extends JsonDeserializer<TextFileInputMeta> implements
        ContextualDeserializer {
    private Class<?> wrappedType;
    private String wrapperKey;


    @Override
    public TextFileInputMeta deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode objectNode = mapper.readTree(parser);
        JsonNode wrapped = objectNode.get(wrapperKey);
        TextFileInputMeta mapped = (TextFileInputMeta)mapIntoObject(wrapped);
        return mapped;
    }

    private Object mapIntoObject(JsonNode node) throws IOException,
            JsonProcessingException {
        JsonParser parser = node.traverse();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(parser, wrappedType);
    }

    @Override
    public JsonDeserializer createContextual(DeserializationConfig config, BeanProperty property) throws JsonMappingException {
        JavaType collectionType = property.getType();
        JavaType collectedType = collectionType.containedType(0);
        wrappedType = collectedType.getRawClass();
        return this;
    }
}
