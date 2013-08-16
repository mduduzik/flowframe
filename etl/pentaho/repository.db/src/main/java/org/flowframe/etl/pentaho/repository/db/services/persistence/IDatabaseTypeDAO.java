package org.flowframe.etl.pentaho.repository.db.services.persistence;

import org.flowframe.etl.pentaho.repository.db.model.DatabaseMetaDTO;
import org.flowframe.etl.pentaho.repository.db.model.PagedDatabaseMetaDTO;
import org.flowframe.etl.pentaho.repository.db.model.PagedDatabaseTypeDTO;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDatabaseTypeDAO {
    public PagedDatabaseTypeDTO search(String descrkeyword, int start, int limit);
    long count(String descrkeyword);
}
