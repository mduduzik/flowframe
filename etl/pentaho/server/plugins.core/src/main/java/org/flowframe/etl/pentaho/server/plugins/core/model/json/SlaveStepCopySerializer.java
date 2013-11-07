package org.flowframe.etl.pentaho.server.plugins.core.model.json;



import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.pentaho.di.trans.SlaveStepCopyPartitionDistribution;

import java.io.IOException;

public class SlaveStepCopySerializer extends JsonSerializer<SlaveStepCopyPartitionDistribution.SlaveStepCopy>
{

    @Override
    public void serialize(SlaveStepCopyPartitionDistribution.SlaveStepCopy value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

    }
}
