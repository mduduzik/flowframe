package org.flowframe.etl.pentaho.repository.db;


import org.flowframe.etl.pentaho.repository.db.resource.DatabaseMetaResource;
import org.flowframe.etl.pentaho.repository.db.resource.DatabaseTypeResource;
import org.flowframe.etl.pentaho.repository.db.resource.RepositoryExplorerResource;
import org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput.CSVInputDialogDelegateResource;
import org.flowframe.etl.pentaho.repository.db.resource.reference.CharsetEncodingResource;
import org.glassfish.jersey.filter.LoggingFilter;
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
        register(LoggingFilter.class);
        register(DatabaseMetaResource.class);
        register(DatabaseTypeResource.class);
        register(RepositoryExplorerResource.class);
        register(CharsetEncodingResource.class);
        register(CSVInputDialogDelegateResource.class);
    }
}
