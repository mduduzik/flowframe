package org.flowframe.etl.pentaho.repository.db.services.persistence;

import org.flowframe.etl.pentaho.repository.db.model.PagedDatabaseTypeDTO;
import org.flowframe.etl.pentaho.repository.db.model.DatabaseTypeDTO;
import org.flowframe.etl.pentaho.repository.db.repository.DBRepositoryWrapperImpl;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("databasetype")
@Component
public class DatabaseTypeDAOImpl implements IDatabaseTypeDAO {
    @Autowired
    private DBRepositoryWrapperImpl repository;

    @Override
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public long count(@QueryParam("query") String descrkeyword) {
        long totalCount = 0;

        try {
            String databaseTypesTable = repository.getRepositoryDatabaseDelegate().quoteTable(KettleDatabaseRepository.TABLE_R_DATABASE_TYPE);
            RowMetaAndData r = repository.getRepositoryConnectionDelegate().getDatabase().getOneRow("SELECT COUNT(*) FROM " + databaseTypesTable + " WHERE description LIKE '%"+descrkeyword+"%'");
            if (r != null)
                totalCount = r.getInteger(0, 0L);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return totalCount;
    }

    // e.g. http://localhost:8082/etlrepo/databasetype/search?descrkeyword=s&start=1&limit=10
    @Override
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public PagedDatabaseTypeDTO search(@QueryParam("query") String descrkeyword, @QueryParam("start") int start, @QueryParam("limit") int limit) {
        DatabaseTypeDTO dbMeta;
        List<DatabaseTypeDTO> results = new ArrayList<DatabaseTypeDTO>();

        String databaseTypesTable = repository.getRepositoryDatabaseDelegate().quoteTable(KettleDatabaseRepository.TABLE_R_DATABASE_TYPE);
        String sql = null;
        if (descrkeyword != null && !descrkeyword.trim().isEmpty())
            sql = "SELECT * FROM " + databaseTypesTable + " WHERE description LIKE '%"+descrkeyword+"%' ORDER BY CODE ASC LIMIT "+limit+" OFFSET "+start;
        else
            sql = "SELECT * FROM " + databaseTypesTable + " ORDER BY DESCRIPTION";

        ResultSet resultSet = null;
        long totalCount = count(descrkeyword);
        try {
            List<Object[]> results_ = repository.getRepositoryConnectionDelegate().getDatabase().getRows(sql, 0);
            for (Object[] result : results_) {
                dbMeta = new DatabaseTypeDTO(new LongObjectId(Long.valueOf(result[0].toString())),
                        result[1].toString(),
                        result[2].toString().replace(',',' '));
                results.add(dbMeta);

            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return new PagedDatabaseTypeDTO(results,totalCount,"data");
    }
}
