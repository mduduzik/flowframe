package org.flowframe.etl.pentaho.server.plugins.core.utils.json;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper>
{
    @Override
    public ObjectMapper getContext(Class<?> type)
    {
        final ObjectMapper result = new ObjectMapper();
        //Deserialization features
        result.getDeserializationConfig().without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);


        //Serialization features
        result.getSerializationConfig().with(SerializationConfig.Feature.REQUIRE_SETTERS_FOR_GETTERS);

/*        SimpleModule module = new SimpleModule(getClass().getName(),new Version(1, 0, 0, null))
                .addDeserializer(PhoneNumber.class, new PhoneNumberDeserializer())
                .addSerializer(PhoneNumber.class, new PhoneNumberSerializer());
        result.registerModule(module);*/
        return result;
    }
}