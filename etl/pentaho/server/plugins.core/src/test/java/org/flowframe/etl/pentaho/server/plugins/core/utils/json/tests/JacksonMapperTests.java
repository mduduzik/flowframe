package org.flowframe.etl.pentaho.server.plugins.core.utils.json.tests;

import junit.framework.TestCase;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapper;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.filter.trans.steps.TextFileInputMetaPropertyFilterMixIn;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;

import java.io.IOException;

/**
 * Created by Mduduzi on 11/5/13.
 */
public class JacksonMapperTests extends TestCase {
    @Ignore
    @Test
    public final void testCustomMapper() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule(getClass().getName(),new Version(1, 0, 0, null));
        mapper.registerModule(module);
        //Deserialization features
        mapper.getDeserializationConfig().without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        //Serialization features
        mapper.getSerializationConfig().with(SerializationConfig.Feature.REQUIRE_SETTERS_FOR_GETTERS);

        final TextFileInputMeta value = new TextFileInputMeta();
        value.setDefault();
        String res = mapper.writeValueAsString(value);
        assertNotNull(res);
    }

    @Ignore
    @Test
    public final void testFilter() throws IOException {
        ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);

        FilterProvider filters = new SimpleFilterProvider().addFilter("TextFileInputMeta", SimpleBeanPropertyFilter.serializeAllExcept(TextFileInputMetaPropertyFilterMixIn.ignorableFieldNames));
        mapper.getSerializationConfig().addMixInAnnotations(TextFileInputMeta.class, TextFileInputMetaPropertyFilterMixIn.class);

        //Deserialization features
        mapper.getDeserializationConfig().without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        //Serialization features
        mapper.getSerializationConfig().with(SerializationConfig.Feature.REQUIRE_SETTERS_FOR_GETTERS);

        final TextFileInputMeta value = new TextFileInputMeta();
        value.setDefault();
        ObjectWriter writer = mapper.writer(filters);
        String res = writer.writeValueAsString(value);
        assertNotNull(res);
    }

    @Test
    public final void testCustomObjectMapper() throws IOException {
        CustomObjectMapper mapper = new  CustomObjectMapper();

        final TextFileInputMeta value = new TextFileInputMeta();
        value.setDefault();
        ObjectWriter writer = mapper.getFilteredWriter();
        String res = writer.writeValueAsString(value);
        assertNotNull(res);
    }
}
