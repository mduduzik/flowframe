package org.flowframe.etl.pentaho.server.plugins.core.resource;


import org.flowframe.etl.pentaho.server.plugins.core.model.DatabaseMetaDTO;
import org.flowframe.etl.pentaho.server.plugins.core.utils.DatabaseMetaUtil;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.etl.pentaho.server.repository.util.RepositoryUtil;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("databasemeta")
@Component
public class DatabaseMetaResource  {
    @Autowired
    private ICustomRepository repository;


    // e.g. http://localhost:8082/etlrepo/databasemeta/get?pathID=/trans/1/database/0
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public DatabaseMetaDTO get(@QueryParam("pathID")String pathID) throws KettleException {
        DatabaseInterface res = DatabaseMetaUtil.getDatabaseMetaByPathId(repository, pathID).getDatabaseInterface();
        if (res == null)
            return null;
        return new DatabaseMetaDTO((LongObjectId)res.getObjectId(),
                res.getName(),
                "",
                res.getPluginId(),
                res.getHostname(),
                res.getDatabaseName(),
                Integer.valueOf(res.getDefaultDatabasePort()),
                res.getUsername(),
                res.getPassword(),
                res.getServername());
    }


    @GET
    @Path("/getall")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DatabaseMetaDTO> getAll(@QueryParam("pathID") String pathID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DatabaseMetaDTO add(@HeaderParam("userid") String userid, DatabaseMetaDTO record) throws KettleException {
        //TODO: Hack
        Organization tenant = new Organization();
        tenant.setId(1L);

        RepositoryDirectoryInterface dir = RepositoryUtil.getDirectory(repository, new LongObjectId(record.getDirObjectId()));
        DatabaseMeta newRecord = DatabaseMetaUtil.addDatabaseMeta(tenant, repository, dir, record);

        return DatabaseMetaDTO.fromMeta(newRecord);
    }


    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public DatabaseMetaDTO update(DatabaseMetaDTO record) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@HeaderParam("userid") String userid, DatabaseMetaDTO record) throws KettleException {
        DatabaseInterface res = DatabaseMetaUtil.getDatabaseMetaByPathId(repository, record.getDirPathId()).getDatabaseInterface();

        //TODO: Hack
        Organization tenant = new Organization();
        tenant.setId(1L);
        DatabaseMetaUtil.deleteDatabaseMeta(tenant,repository,res.getObjectId());

        DatabaseMetaUtil.deleteDatabaseMetaByPathId(repository,record.getDirPathId());
        return Response.ok("DB Connection " + res.getName() + " deleted successfully", MediaType.TEXT_PLAIN).build();
    }
}
