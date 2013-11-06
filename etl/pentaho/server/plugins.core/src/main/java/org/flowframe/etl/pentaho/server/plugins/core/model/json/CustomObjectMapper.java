package org.flowframe.etl.pentaho.server.plugins.core.model.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.filter.trans.steps.TextFileInputMetaPropertyFilterMixIn;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;

/**
 * Created by Mduduzi on 11/5/13.
 */
public class CustomObjectMapper extends ObjectMapper {
    private final SimpleModule module;
    private SimpleFilterProvider filters;

    public CustomObjectMapper() {
        super();
        setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        this.module = new SimpleModule(getClass().getName(),new Version(1, 0, 0, null));
        registerModule(module);

        //Deserialization features
        getDeserializationConfig().without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

        //Serialization features
        getSerializationConfig().with(SerializationConfig.Feature.INDENT_OUTPUT);
        getSerializationConfig().with(SerializationConfig.Feature.REQUIRE_SETTERS_FOR_GETTERS);

        initFilters();
    }

    private void initFilters(){
        this.filters = new SimpleFilterProvider().addFilter("TextFileInputMeta", SimpleBeanPropertyFilter.serializeAllExcept(TextFileInputMetaPropertyFilterMixIn.ignorableFieldNames));
        getSerializationConfig().addMixInAnnotations(TextFileInputMeta.class, TextFileInputMetaPropertyFilterMixIn.class);
    }

    public ObjectWriter getFilteredWriter() {
       return this.writer(this.filters);
    }
}
