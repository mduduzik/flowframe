package org.flowframe.etl.pentaho.server.repository.db.repository;


import org.flowframe.etl.pentaho.server.repository.db.model.DatabaseMetaDTO;
import org.flowframe.etl.pentaho.server.repository.db.model.DirectoryDTO;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

import java.sql.ResultSet;
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
        dbMeta.getAttributes().put(ATTRIBUTE_TENANT_ORG_ID,tenant.getId().toString());
        dbMeta.getAttributes().put(ATTRIBUTE_PARENT_DIR_OBJID,dir.getObjectId().toString());
    }

    public static void unsetTenantAndDirAttributes(DatabaseMeta dbMeta) {
        dbMeta.getAttributes().remove(ATTRIBUTE_TENANT_ORG_ID);
        dbMeta.getAttributes().remove(ATTRIBUTE_PARENT_DIR_OBJID);
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

        repo.getRepositoryDatabaseDelegate().saveDatabaseMeta(newDatabaseMeta);

        repo.getRepositoryConnectionDelegate().commit();

        return newDatabaseMeta;
    }

    public static DatabaseMeta getDatabaseMeta(CustomRepository repo, ObjectId dbId) throws KettleException {
        DatabaseMeta databaseMeta = repo.getRepositoryDatabaseDelegate().loadDatabaseMeta(dbId);
        return databaseMeta;
    }

    public static DatabaseMeta getDatabaseMetaByPathId(CustomRepository repo, String pathId) throws KettleException {
        ObjectId objId = RepositoryUtil.getDBObjectIDFromPathID(pathId);
        DatabaseMeta databaseMeta = repo.getRepositoryDatabaseDelegate().loadDatabaseMeta(objId);
        return databaseMeta;
    }

    public static boolean deleteDatabaseMetaByPathId(CustomRepository repo, String pathId) throws KettleException {
        ObjectId objId = RepositoryUtil.getDBObjectIDFromPathID(pathId);
        repo.getRepositoryDatabaseDelegate().delDatabase(objId);
        return true;
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

        repo.getRepositoryConnectionDelegate().commit();

        return databaseMeta;
    }

    public static void deleteDatabaseDirectory(CustomRepository repo, RepositoryDirectoryInterface dir, String tenantId) throws KettleException {
        //Delete all databases
        Collection<ObjectId> dbIds = getDatabaseIdsBySubDirAndTenantId(tenantId, dir, repo);
        for (ObjectId dbId : dbIds) {
            repo.getRepositoryDatabaseDelegate().delDatabase(dbId);
        }

        //Delete dir
        repo.getRepositoryDirectoryDelegate().deleteDirectory(dir);

        repo.getRepositoryConnectionDelegate().commit();
    }


    public static DirectoryDTO addDatabaseDirectory(CustomRepository repo, RepositoryDirectoryInterface parentDir, Organization tenant, DirectoryDTO dir) throws KettleException {
        RepositoryDirectoryInterface newDir = null;
        if (parentDir == null) {//@DB Connections level
            parentDir = repo.provideDBConnectionsMetadataDirectoryForTenant(tenant);
        }

        newDir = repo.getRepositoryDirectoryDelegate().createRepositoryDirectory(parentDir, dir.getName());
        repo.getRepositoryConnectionDelegate().commit();

        dir.setDirObjectId(((LongObjectId)newDir.getObjectId()).longValue());

        return dir;
    }

    public static void deleteDatabaseMeta(Organization tenant, CustomRepository repo, ObjectId objId) throws KettleException {
        DatabaseMeta databaseMeta = repo.getRepositoryDatabaseDelegate().loadDatabaseMeta(objId);

        unsetTenantAndDirAttributes(databaseMeta);

        //Update attr removal
        repo.getRepositoryDatabaseDelegate().saveDatabaseMeta(databaseMeta);
        repo.getRepositoryConnectionDelegate().commit();

        //Finally delete it...
        repo.getRepositoryDatabaseDelegate().delDatabase(objId);

        repo.getRepositoryConnectionDelegate().commit();
    }

    public static List<DatabaseMeta> getDatabasesBySubDirAndTenantId(CustomRepository repo, RepositoryDirectoryInterface dir, String tenantId) throws KettleException {
        Collection<ObjectId> dbIds = getDatabaseIdsBySubDirAndTenantId(tenantId, dir, repo);
        final List<DatabaseMeta> dbs = new ArrayList<DatabaseMeta>();
        for (ObjectId dbId : dbIds) {
             dbs.add(repo.getRepositoryDatabaseDelegate().loadDatabaseMeta(dbId));
        }

        return dbs;
    }

    public static Collection<ObjectId> getDatabaseIdsBySubDirAndTenantId(String tenantId, RepositoryDirectoryInterface dir, CustomRepository repository) throws KettleException {
        String sql = "SELECT "+"db.ID_DATABASE"+" FROM "+quoteTable(repository,KettleDatabaseRepository.TABLE_R_DATABASE)+" db"+
            " JOIN "+quoteTable(repository,KettleDatabaseRepository.TABLE_R_DATABASE_ATTRIBUTE)+" tenantprop ON db.ID_DATABASE = tenantprop.ID_DATABASE AND tenantprop.CODE = 'TENANT_ID'"+
            " JOIN "+quoteTable(repository,KettleDatabaseRepository.TABLE_R_DATABASE_ATTRIBUTE)+" dirprop ON db.ID_DATABASE = dirprop.ID_DATABASE AND dirprop.CODE = 'DIR_OBJID'"+
            " WHERE tenantprop.VALUE_STR  = "+tenantId+" AND dirprop.VALUE_STR = "+dir.getObjectId().toString();
        List<ObjectId> dbIds = new ArrayList<ObjectId>();


        ResultSet rs = null;
        Database db = null;
        try {
            db = new Database(repository.getSupportingDatabase().getDatabaseMeta());
            db.connect();
            rs = db.openQuery(sql);
            List<Object[]> rows = db.getRows(rs, 0, null);
            for (Object[] row : rows)
            {
                dbIds.add(new LongObjectId(Long.valueOf(row[0].toString())));
            }
            //repository.getSupportingDatabase().disconnect();
        } finally {
           if (rs != null && db != null)
             db.closeQuery(rs);
           if (db != null)
             db.disconnect();
        }

        return dbIds;
    }

/*    public static Map<String,Collection<String>> getDatabaseIdsMapBySubDirAndTenantId(String tenantId, CustomRepository repository) throws KettleException {
        String sql = "SELECT "+"db.ID_DATABASE, dirprop.VALUE_STR FROM "+quoteTable(repository,KettleDatabaseRepository.TABLE_R_DATABASE)+" db"+
                " JOIN "+quoteTable(repository,KettleDatabaseRepository.TABLE_R_DATABASE_ATTRIBUTE)+" tenantprop ON db.ID_DATABASE = tenantprop.ID_DATABASE AND tenantprop.CODE = 'TENANT_ID'"+
                " JOIN "+quoteTable(repository,KettleDatabaseRepository.TABLE_R_DATABASE_ATTRIBUTE)+" dirprop ON db.ID_DATABASE = dirprop.ID_DATABASE AND dirprop.CODE = 'DIR_OBJID'"+
                " WHERE tenantprop.VALUE_STR  = "+tenantId;
        List<ObjectId> dbIds = new ArrayList<ObjectId>();

        Map<String,Collection<String>> map = new HashMap<String, Collection<String>>();
        Collection<String> dbs = null;
        List<Object[]> rows = repository.getRepositoryConnectionDelegate().getRows(sql, 0);
        for (Object[] row : rows)
        {
            dbs = map.get(row[0].toString());
            if (dbs == null) {
                dbs = new ArrayList<String>();
                map.put(row[0].toString(),dbs);
            }
            dbs.add(row[1].toString());
        }


        return map;
    }*/

    public static String quote(CustomRepository repository, String identifier) {
        return repository.getRepositoryConnectionDelegate().getDatabaseMeta().quoteField(identifier);
    }

    public static String quoteTable(CustomRepository repository, String table) {
        return repository.getRepositoryConnectionDelegate().getDatabaseMeta().getQuotedSchemaTableCombination(null, table);
    }
}
