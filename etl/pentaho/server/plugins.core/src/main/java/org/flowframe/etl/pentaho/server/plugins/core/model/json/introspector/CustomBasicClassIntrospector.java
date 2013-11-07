package org.flowframe.etl.pentaho.server.plugins.core.model.json.introspector;

import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.BasicClassIntrospector;
import org.codehaus.jackson.map.introspect.POJOPropertiesCollector;
import org.codehaus.jackson.type.JavaType;

/**
 * Created by Mduduzi on 11/6/13.
 */
public class CustomBasicClassIntrospector extends BasicClassIntrospector {
    public CustomBasicClassIntrospector() {
        super();
    }

    /**
     * Overridable method called for creating {@link org.codehaus.jackson.map.introspect.POJOPropertiesCollector} instance
     * to use; override is needed if a custom sub-class is to be used.
     *
     * @since 1.9
     */
    protected POJOPropertiesCollector constructPropertyCollector(MapperConfig<?> config,
                                                                 AnnotatedClass ac, JavaType type,
                                                                 boolean forSerialization)
    {
        return new CustomPOJOPropertiesCollector(config, forSerialization, type, ac);
    }
}
