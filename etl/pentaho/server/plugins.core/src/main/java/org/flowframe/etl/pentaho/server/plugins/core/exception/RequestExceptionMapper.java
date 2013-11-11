package org.flowframe.etl.pentaho.server.plugins.core.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Mduduzi on 10/30/13.
 */
@Provider
public class RequestExceptionMapper implements ExceptionMapper<RequestException> {
    public Response toResponse(RequestException ex) {
        return Response.status(400).
                entity(ex.getMessage()).
                type("text/plain").
                entity(formatException(ex)).
                build();
    }

    protected String formatException(Exception e) {
        final StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        final String stacktrace = sw.toString();


        return stacktrace;
    }
}
