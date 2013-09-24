package org.flowframe.etl.pentaho.server.repository.db;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 7:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainApplication extends ResourceConfig {
    /**
     * Register JAX-RS application components.
     */
    public MainApplication () {
        //packages("org.flowframe.etl.pentaho.repository.db.resource;org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput");
        register(RequestContextFilter.class);
        register(MultiPartFeature.class);
        register(JacksonFeature.class);
        register(LoggingFilter.class);
    }
}
