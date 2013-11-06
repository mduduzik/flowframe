package org.flowframe.etl.pentaho.server.plugins.core.model.json;

import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Created by Mduduzi on 11/5/13.
 */


@Provider
@Produces(MediaType.APPLICATION_JSON)
public class CustomObjectMapperResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public CustomObjectMapperResolver() {
        mapper = new CustomObjectMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

}
