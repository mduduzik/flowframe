package org.flowframe.etl.pentaho.server.plugins.core.model.json.deserializer;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TextFileInputMetaDeserializer extends JsonDeserializer<TextFileInputMeta>
{
    public static List<String> ignorableFieldNames = Arrays.asList("distribution",
            "databases",
            "repository",
            "parentStepMeta",
            "ioMeta",
            "tableFields",
            "requiredFields",
            "objectsMap",
            "resourceDependencies",
            "sQLStatements",
            "paused","usedArguments","xml","fileFormatTypeNr","fileTypeNr","log","stepData","stepIOMeta","requiredFields","stopped","running","acceptingStep");

    public TextFileInputMetaDeserializer() {
    }

    @Override
    public TextFileInputMeta deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        Iterator<Map.Entry<String, JsonNode>> it = node.getFields();
        Map.Entry<String, JsonNode> entry = null;

        TextFileInputMeta res = new TextFileInputMeta();

        while (it.hasNext()) {
            entry = it.next();
            if (ignorableFieldNames.contains(entry.getKey()))
                continue;


        }
        return null;
    }
}
