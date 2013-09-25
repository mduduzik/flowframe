package org.flowframe.etl.pentaho.server.plugins.core.model;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.repository.LongObjectId;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 11:43 PM
 * To
 */
public class DatabaseMetaDTO implements Serializable {
    private int index;
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
    private String dirPathId;
    private long dirObjectId;

    public DatabaseMetaDTO() {}

    public DatabaseMetaDTO(LongObjectId objId,
                           String accessType,
                           String databaseType,
                           String databaseTypeDesc) {
        this.objectId = ((LongObjectId)objId).longValue();
        this.accessType = accessType;
        this.databaseType = databaseType;
    }

    public DatabaseMetaDTO(int index,
                           String accessType,
                           String databaseType,
                           String databaseTypeDesc) {
        this.index = index;
        this.accessType = accessType;
        this.databaseType = databaseType;
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
        this(name,accessType,databaseType,hostname,databaseName,databasePort,username,password,serverName);
        this.objectId = ((LongObjectId)objId).longValue();
    }

    public DatabaseMetaDTO(int index,
                           String name,
                           String accessType,
                           String databaseType,
                           String hostname,
                           String databaseName,
                           int databasePort,
                           String username,
                           String password,
                           String serverName) {
        this(name,accessType,databaseType,hostname,databaseName,databasePort,username,password,serverName);
        this.index = index;
    }

    public DatabaseMetaDTO(String name,
                           String accessType,
                           String databaseType,
                           String hostname,
                           String databaseName,
                           int databasePort,
                           String username,
                           String password,
                           String serverName) {
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public String getDirPathId() {
        return dirPathId;
    }

    // eg /dev/1
    public void setDirPathId(String dirPathId) {
        this.dirPathId = dirPathId;
    }

    public long getDirObjectId() {
        return dirObjectId;
    }

    public void setDirObjectId(long dirObjectId) {
        this.dirObjectId = dirObjectId;
    }

    public static DatabaseMeta toMeta(DatabaseMetaDTO dto) {
        DatabaseMeta meta = new DatabaseMeta();
        meta.setName(dto.getName());
        meta.setDatabaseType(dto.getDatabaseType());
        meta.setDBName(dto.getDatabaseName());
        meta.setDBPort(Integer.toString(dto.getDatabasePort()));
        meta.setHostname(dto.getHostname());
        meta.setUsername(dto.getUsername());
        meta.setPassword(dto.getPassword());
        return meta;
    }

    public static DatabaseMetaDTO fromMeta(DatabaseMeta meta) {
        DatabaseMetaDTO dto = new DatabaseMetaDTO((LongObjectId)meta.getObjectId(),
                meta.getName(),
                meta.getAccessTypeDesc(),
                meta.getDatabaseTypeDesc(),
                meta.getHostname(),
                meta.getDatabaseName(),
                meta.getDefaultDatabasePort(),
                meta.getUsername(),
                meta.getPassword(),
                meta.getServername());

        return dto;
    }
}
