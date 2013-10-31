package org.flowframe.etl.pentaho.server.plugins.core.exception;

import org.pentaho.di.core.exception.KettleDependencyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Mduduzi on 10/30/13.
 */
@Provider
public class KettleDependencyExceptionMapper implements ExceptionMapper<KettleDependencyException> {
    public Response toResponse(KettleDependencyException ex) {
        return Response.status(404).
                entity(ex.getMessage()).
                type("text/plain").
                build();
    }
}
