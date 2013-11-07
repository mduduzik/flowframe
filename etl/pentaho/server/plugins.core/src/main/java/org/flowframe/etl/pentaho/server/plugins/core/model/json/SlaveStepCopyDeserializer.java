package org.flowframe.etl.pentaho.server.plugins.core.model.json;



import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.pentaho.di.trans.SlaveStepCopyPartitionDistribution;

import java.io.IOException;

public class SlaveStepCopyDeserializer extends JsonDeserializer<SlaveStepCopyPartitionDistribution.SlaveStepCopy>
{
    @Override
    public SlaveStepCopyPartitionDistribution.SlaveStepCopy deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
/*        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        return new User(null,
                node.get("username").getTextValue(),
                node.get("password").getTextValue());*/
        return null;
    }
}
