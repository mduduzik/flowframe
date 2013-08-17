package org.flowframe.etl.pentaho.repository.db.repository;

import org.flowframe.etl.pentaho.repository.db.model.DatabaseMetaDTO;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.database.BaseDatabaseMeta;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.delegates.KettleDatabaseRepositoryDatabaseDelegate;
import org.pentaho.di.trans.TransMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/17/13
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseMetaUtil {
    public static final String ATTRIBUTE_TENANT_ORG_ID            = "TENANT_ID";
    public static final String ATTRIBUTE_PARENT_DIR_OBJID         = "DIR_OBJID";

    /**
     *
     * Attributes
     */
    public static void setTenantAndDirAttributes(Organization tenant, RepositoryDirectoryInterface dir, DatabaseMeta dbMeta) {
        dbMeta.getAttributes().put(ATTRIBUTE_TENANT_ORG_ID,tenant.getId());
        dbMeta.getAttributes().put(ATTRIBUTE_TENANT_ORG_ID,dir.getObjectId().toString());
    }

    /**
     *    Add/Update/Delete Metadata Steps
     */
    //-- Databases Meta
    public static DatabaseMeta addDatabaseMeta(Organization tenant, CustomRepository repo, RepositoryDirectoryInterface dir, DatabaseMetaDTO databaseMeta) throws KettleException {
        ObjectId id = repo.getRepositoryDatabaseDelegate().insertDatabase(databaseMeta.getName(),
                databaseMeta.getDatabaseType(),
                databaseMeta.getAccessType(),
                databaseMeta.getHostname(),
                databaseMeta.getDatabaseName(),
                Integer.toString(databaseMeta.getDatabasePort()),
                databaseMeta.getUsername(),
                databaseMeta.getPassword(),
                databaseMeta.getPassword(),
                null,
                null);
        DatabaseMeta newDatabaseMeta = repo.getRepositoryDatabaseDelegate().loadDatabaseMeta(id);
        setTenantAndDirAttributes(tenant, dir, newDatabaseMeta);

        return newDatabaseMeta;
    }

    public static DatabaseMeta updateDatabaseMeta(CustomRepository repo, RepositoryDirectoryInterface dir, DatabaseMetaDTO dto) throws KettleException {
        ObjectId id = new LongObjectId(dto.getObjectId());
        DatabaseMeta databaseMeta = repo.getRepositoryDatabaseDelegate().loadDatabaseMeta(id);

        databaseMeta.setDatabaseType(dto.getDatabaseType());
        databaseMeta.setHostname(dto.getHostname());
        databaseMeta.setDBName(dto.getDatabaseName());
        databaseMeta.setDBPort(Integer.toString(dto.getDatabasePort()));
        databaseMeta.setUsername(dto.getUsername());
        databaseMeta.setPassword(dto.getPassword());

        repo.getRepositoryDatabaseDelegate().saveDatabaseMeta(databaseMeta);

        databaseMeta = repo.getRepositoryDatabaseDelegate().loadDatabaseMeta(id);

        return databaseMeta;
    }

    public static void deleteDatabaseMeta(CustomRepository repo, RepositoryDirectoryInterface dir, DatabaseMetaDTO dto) throws KettleException {
        ObjectId id = new LongObjectId(dto.getObjectId());
        repo.getRepositoryDatabaseDelegate().delDatabase(id);
    }

    public static List<DatabaseMeta> getDatabasesByTenant(CustomRepository repo, RepositoryDirectoryInterface dir, String tenantId) throws KettleException {
        Collection<ObjectId> dbIds = getDatabaseAttributes(tenantId, dir, repo);
        final List<DatabaseMeta> dbs = new ArrayList<DatabaseMeta>();
        for (ObjectId dbId : dbIds) {
             dbs.add(repo.getRepositoryDatabaseDelegate().loadDatabaseMeta(dbId));
        }

        return dbs;
    }

    public static Collection<ObjectId> getDatabaseAttributes(String tenantId, RepositoryDirectoryInterface dir, CustomRepository repository) throws KettleDatabaseException, KettleValueException
    {
        String sql = "SELECT "+"attr.ID_DATABASE"+" FROM "+quoteTable(repository,KettleDatabaseRepository.TABLE_R_DATABASE_ATTRIBUTE)+" attr"+
            "LEFT JOIN "+quoteTable(repository,KettleDatabaseRepository.TABLE_R_DATABASE)+" db ON attr.ID_DATABASE_ATTRIBUTE = db.ID_DATABASE"+
            "WHERE attr.CODE = 'TENANT_ID' AND attr.VALUE_STR = "+tenantId+" AND attr.CODE = 'DIR_OBJID' AND attr.VALUE_STR = "+dir.getObjectId().toString();

        List<ObjectId> dbIds = new ArrayList<ObjectId>();
        List<Object[]> rows = repository.getRepositoryConnectionDelegate().getRows(sql, 0);
        for (Object[] row : rows)
        {
            dbIds.add(new LongObjectId(Long.valueOf(row[0].toString())));
        }
        return dbIds;
    }

    public static String quote(CustomRepository repository, String identifier) {
        return repository.getRepositoryConnectionDelegate().getDatabaseMeta().quoteField(identifier);
    }

    public static String quoteTable(CustomRepository repository, String table) {
        return repository.getRepositoryConnectionDelegate().getDatabaseMeta().getQuotedSchemaTableCombination(null, table);
    }
}
