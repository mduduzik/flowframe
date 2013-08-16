package org.flowframe.etl.pentaho.repository.db.services.persistence;

import org.flowframe.etl.pentaho.repository.db.model.DatabaseMetaDTO;
import org.flowframe.etl.pentaho.repository.db.repository.DBRepositoryWrapperImpl;
import org.flowframe.etl.pentaho.repository.db.repository.IdentityUtil;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
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
public class DatabaseMetaDAOImpl implements IDatabaseMetaDAO {
    @Autowired
    private DBRepositoryWrapperImpl repository;


    // e.g. http://localhost:8082/etlrepo/databasemeta/get?pathID=/trans/1/database/0
    @Override
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public DatabaseMetaDTO get(@QueryParam("pathID")String pathID) throws KettleException {
        DatabaseMeta res = IdentityUtil.getDatabase(repository, pathID);
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

    @Override
    @GET
    @Path("/getall")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DatabaseMetaDTO> getAll(@QueryParam("pathID") String pathID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(DatabaseMetaDTO record) throws KettleException {
        repository.getRepositoryDatabaseDelegate().insertDatabase(record.getName(),
                record.getDatabaseType(),
                record.getAccessType(),
                record.getHostname(),
                record.getDatabaseName(),
                Integer.toString(record.getDatabasePort()),
                record.getUsername(),
                record.getPassword(),
                record.getServerName(),
                null,
                null);

        String result = "DatabaseMeta saved : " + record;
        return Response.status(201).entity(result).build();
    }

    @Override
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public DatabaseMetaDTO update(DatabaseMetaDTO record) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public DatabaseMetaDTO delete(DatabaseMeta record) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
