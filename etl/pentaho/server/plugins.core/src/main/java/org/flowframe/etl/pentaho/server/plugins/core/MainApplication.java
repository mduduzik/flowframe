package org.flowframe.etl.pentaho.server.plugins.core;

import org.flowframe.etl.pentaho.server.plugins.core.exception.KettleDependencyExceptionMapper;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapperResolver;
import org.flowframe.etl.pentaho.server.plugins.core.resource.*;
import org.flowframe.etl.pentaho.server.plugins.core.resource.carte.TransformationJobServiceResource;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.csvinput.CSVInputDialogDelegateResource;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.textfileinput.TextFileInputDialogDelegateResource;
import org.flowframe.etl.pentaho.server.plugins.core.resource.reference.CharsetEncodingResource;
import org.flowframe.etl.pentaho.server.plugins.core.resource.repository.doclib.DocLibExplorerResource;
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
        register(RequestContextFilter.class);
        register(MultiPartFeature.class);
        register(JacksonFeature.class);
        register(LoggingFilter.class);
        register(CustomObjectMapperResolver.class);


        register(DatabaseMetaResource.class);
        register(DatabaseTypeResource.class);
        register(RepositoryExplorerResource.class);
        register(DocLibExplorerResource.class);
        register(CharsetEncodingResource.class);
        register(CSVInputDialogDelegateResource.class);
        register(TransformationMetaResource.class);
        register(TransformationJobServiceResource.class);
        register(JobMetaResource.class);
        register(TextFileInputDialogDelegateResource.class);

        // Exceptions
        register(KettleDependencyExceptionMapper.class);
    }
}
