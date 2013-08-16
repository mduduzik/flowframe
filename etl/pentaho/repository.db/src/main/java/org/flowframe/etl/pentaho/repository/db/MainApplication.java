package org.flowframe.etl.pentaho.repository.db;

import org.flowframe.etl.pentaho.repository.db.services.persistence.DatabaseMetaDAOImpl;
import org.flowframe.etl.pentaho.repository.db.services.persistence.DatabaseTypeDAOImpl;
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
        register(RequestContextFilter.class);
        register(DatabaseMetaDAOImpl.class);
        register(DatabaseTypeDAOImpl.class);
    }
}
