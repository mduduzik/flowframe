package org.flowframe.etl.pentaho.server.plugins.core.resource;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.ResultSet;
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
public class DatabaseTypeResource  {
    @Autowired
    private ICustomRepository repository;


    public long count(Database dbInstance, String descrkeyword) {
        long totalCount = 0;

        try {
            String databaseTypesTable = repository.getRepositoryDatabaseDelegate().quoteTable(KettleDatabaseRepository.TABLE_R_DATABASE_TYPE);
            RowMetaAndData r = null;
            if (descrkeyword != null && !descrkeyword.isEmpty())
                r = dbInstance.getOneRow("SELECT COUNT(*) FROM " + databaseTypesTable + " WHERE description LIKE '%" + descrkeyword + "%'");
            else
                r = dbInstance.getOneRow("SELECT COUNT(*) FROM " + databaseTypesTable);
            if (r != null)
                totalCount = r.getInteger(0, 0L);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return totalCount;
    }

    // e.g. http://localhost:8082/etlrepo/databasetype/search?descrkeyword=s&start=1&limit=10
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public String search(@QueryParam("query") String descrkeyword, @QueryParam("callback") String callback, @QueryParam("start") int start, @QueryParam("limit") int limit) {
        JSONObject wrapper = new JSONObject();
        JSONArray types = new JSONArray();

        String databaseTypesTable = repository.getRepositoryDatabaseDelegate().quoteTable(KettleDatabaseRepository.TABLE_R_DATABASE_TYPE);
        String sql = null;
        if (descrkeyword != null && !descrkeyword.trim().isEmpty())
            sql = "SELECT * FROM " + databaseTypesTable + " WHERE description LIKE '%"+descrkeyword+"%' ORDER BY CODE ASC LIMIT "+limit+" OFFSET "+start;
        else
            sql = "SELECT * FROM " + databaseTypesTable + " ORDER BY DESCRIPTION";

        Database dbInstance = null;
        try {
            dbInstance = new Database(repository.getPooledDBConnectionMeta());
            try {
                dbInstance.connect();
            } catch (Exception e) {
                throw e;
            }


            ResultSet resultSet = null;
            long totalCount = count(dbInstance,descrkeyword);
            List<Object[]> results_ = dbInstance.getRows(sql, 0);
            for (Object[] result : results_) {
                JSONObject obj = new JSONObject();
                obj.put("id", Integer.valueOf(result[0].toString()));
                obj.put("code",result[1].toString());
                obj.put("description",result[2].toString().replace(',',' '));
                types.put(obj);
            }

            wrapper.put("totalCount",totalCount);
            wrapper.put("data",types);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (dbInstance != null)
                dbInstance.disconnect();
        }

        if (callback != null)
            return callback+"("+wrapper.toString()+");";
        else
            return wrapper.toString();
    }
}
