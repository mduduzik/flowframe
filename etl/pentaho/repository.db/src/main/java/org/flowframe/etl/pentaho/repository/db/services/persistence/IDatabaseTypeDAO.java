package org.flowframe.etl.pentaho.repository.db.services.persistence;

import org.flowframe.etl.pentaho.repository.db.model.DatabaseMetaDTO;
import org.flowframe.etl.pentaho.repository.db.model.PagedDatabaseMetaDTO;
import org.flowframe.etl.pentaho.repository.db.model.PagedDatabaseTypeDTO;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDatabaseTypeDAO {
    long count(String descrkeyword);

    // e.g. http://localhost:8082/etlrepo/databasetype/search?descrkeyword=s&start=1&limit=10
    @GET
    @Path("/search")
    @Produces(MediaType.TEXT_PLAIN)
    String search(@QueryParam("query") String descrkeyword, @QueryParam("callback") String callback, @QueryParam("start") int start, @QueryParam("limit") int limit);
}
