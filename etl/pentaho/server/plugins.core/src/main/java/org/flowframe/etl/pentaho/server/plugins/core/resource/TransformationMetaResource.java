package org.flowframe.etl.pentaho.server.plugins.core.resource;

import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("transmeta")
@Component
public class TransformationMetaResource {
    @Autowired
    private ICustomRepository repository;
}
