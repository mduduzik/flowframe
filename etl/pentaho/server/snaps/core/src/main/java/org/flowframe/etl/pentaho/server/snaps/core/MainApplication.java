package org.flowframe.etl.pentaho.server.snaps.core;

import org.flowframe.etl.pentaho.server.snaps.core.resource.DatabaseMetaResource;
import org.flowframe.etl.pentaho.server.snaps.core.resource.DatabaseTypeResource;
import org.flowframe.etl.pentaho.server.snaps.core.resource.RepositoryExplorerResource;
import org.flowframe.etl.pentaho.server.snaps.core.resource.etl.trans.steps.csvinput.CSVInputDialogDelegateResource;
import org.flowframe.etl.pentaho.server.snaps.core.resource.reference.CharsetEncodingResource;
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
    public MainApplication() {
        //packages("org.flowframe.etl.pentaho.repository.db.resource;org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput");
        register(DatabaseTypeResource.class);
        register(RequestContextFilter.class);
        register(MultiPartFeature.class);
        register(JacksonFeature.class);
        register(LoggingFilter.class);
        register(DatabaseMetaResource.class);
        register(DatabaseTypeResource.class);
        register(RepositoryExplorerResource.class);
        register(CharsetEncodingResource.class);
        register(CSVInputDialogDelegateResource.class);
    }
}
