package org.flowframe.etl.pentaho.server.plugins.core.model.json;

import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.deser.StdDeserializerProvider;
import org.codehaus.jackson.type.JavaType;

/**
 * Created by Mduduzi on 11/6/13.
 */
public class CustomStdDeserializerProvider extends StdDeserializerProvider {

    public CustomStdDeserializerProvider() {
        super();
    }

    public CustomStdDeserializerProvider(DeserializerFactory f) {
        super(f);
    }

    @Override
    public KeyDeserializer findKeyDeserializer(DeserializationConfig config, JavaType type, BeanProperty property) throws JsonMappingException {
        if (type.toString().indexOf("SharedObjects") >= 0
            || type.toString().indexOf("SlaveStepCopy") >= 0
            || type.toString().indexOf("DBCacheEntry") >= 0)
            return null;
        else
            return super.findKeyDeserializer(config, type, property);
    }

    @Override
    public StdDeserializerProvider withFactory(DeserializerFactory factory) {
        return new CustomStdDeserializerProvider(factory);
    }
}
