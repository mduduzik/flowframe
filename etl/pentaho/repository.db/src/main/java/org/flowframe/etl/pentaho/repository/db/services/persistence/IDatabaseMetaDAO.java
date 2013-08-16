package org.flowframe.etl.pentaho.repository.db.services.persistence;

import org.flowframe.etl.pentaho.repository.db.model.DatabaseMetaDTO;
import org.flowframe.etl.pentaho.repository.db.model.PagedDatabaseMetaDTO;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDatabaseMetaDAO {
    public DatabaseMetaDTO get(String pathID) throws KettleException;
    public List<DatabaseMetaDTO> getAll(String pathID) throws KettleException;

    public Response add(DatabaseMetaDTO record) throws KettleException;

    public DatabaseMetaDTO update(DatabaseMetaDTO record);

    public DatabaseMetaDTO delete(DatabaseMeta record);
}
