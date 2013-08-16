package org.flowframe.etl.pentaho.repository.db.model;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.repository.LongObjectId;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 11:43 PM
 * To
 */
public class DatabaseMetaDTO {
    private long objectId;
    private String name;
    private String accessType;
    private String databaseType;
    private String hostname;
    private String databaseName;
    private int databasePort;
    private String username;
    private String password;
    private String serverName;

    public DatabaseMetaDTO() {}

    public DatabaseMetaDTO(LongObjectId objId,
                           String accessType,
                           String databaseType,
                           String databaseTypeDesc) {
        objectId = ((LongObjectId)objId).longValue();
        accessType = accessType;
        databaseType = databaseType;
    }

    public DatabaseMetaDTO(LongObjectId objId,
                           String name,
                           String accessType,
                           String databaseType,
                           String hostname,
                           String databaseName,
                           int databasePort,
                           String username,
                           String password,
                           String serverName) {
        this.objectId = ((LongObjectId)objId).longValue();
        this.name = name;
        this.accessType = accessType;
        this.databaseType = databaseType;
        this.hostname = hostname;
        this.databaseName = databaseName;
        this.databasePort = databasePort;
        this.username = username;
        this.password = password;
        this.serverName = serverName;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(int databasePort) {
        this.databasePort = databasePort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
