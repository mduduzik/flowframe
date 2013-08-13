package com.conx.bi.etl.pentaho.repository.db.services.repository;

import com.conx.bi.etl.pentaho.repository.db.services.CustomRepository;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.core.database.Database;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/13/13
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class RepositoryExporter {

    public static JSONArray exportTreeToJSONByTenant(ProgressMonitorListener monitor, CustomRepository repo, Organization tenant) {
        JSONArray treeByTenant = new JSONArray();
        try {
            RepositoryDirectoryInterface mdDir = repo.provideMetadataDirectoryForTenant(tenant);

            RepositoryDirectoryInterface dbConnectionsMdDir = repo.provideDBConnectionsMetadataDirectoryForTenant(tenant);
            RepositoryDirectoryInterface excelMdDir = repo.provideExcelMetadataDirectoryForTenant(tenant);
            RepositoryDirectoryInterface delimitedMdDir = repo.provideDelimitedMetadataDirectoryForTenant(tenant);

            JSONObject metadata = new JSONObject();
            metadata.put("id","metadata");
            metadata.put("text",mdDir.getName());
            metadata.put("title",mdDir.getName());
            metadata.put("icon","/oryx/images/conxbi/etl/package.gif");
            metadata.put("leaf",false);
            metadata.put("hasChildren",true);
            metadata.put("singleClickExpand",true);
            JSONArray metadataChildren = new JSONArray();
            treeByTenant.put(metadata);

            //-- DB Connections
            JSONObject metadataDBConnections = generateDBConnectionsJSON(repo,dbConnectionsMdDir);
            metadataDBConnections.put("id","metadata.dbconnections");
            metadataDBConnections.put("text","DB Connections");
            metadataDBConnections.put("title","DB Connections");
            metadataDBConnections.put("icon","/oryx/images/conxbi/etl/connection.gif");
            metadataDBConnections.put("leaf",false);
            metadataDBConnections.put("hasChildren",true);
            metadataDBConnections.put("singleClickExpand",true);

            metadataChildren.put(metadataDBConnections);
            metadata.put("children",metadataChildren);

        } catch (KettleException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return treeByTenant;
    }

    private static JSONObject generateDBConnectionsJSON(CustomRepository customRepository, RepositoryDirectoryInterface dir) {
        JSONObject subDir = new JSONObject();
        try {
            // Populate
            subDir.put("id", dir.getObjectId().toString());
            subDir.put("text", dir.getName());
            subDir.put("title",dir.getName());
            subDir.put("icon", "/oryx/images/conxbi/etl/folder_close.png");
            subDir.put("leaf",false);
            subDir.put("hasChildren",true);
            subDir.put("singleClickExpand",true);

            JSONArray childrenArray = new JSONArray();

            //Do sub dirs
            List<RepositoryDirectoryInterface> dbConnectionsMdSubDirs = dir.getChildren();

            for (RepositoryDirectoryInterface subDir_ : dbConnectionsMdSubDirs) {
                JSONObject subDirDir = generateDBConnectionsJSON(customRepository, subDir_);
                childrenArray.put(subDirDir);
            }


            //Do actual db connections
            String[] dbConnectionMetadataTransNames = customRepository.getTransformationNames(dir.getObjectId(), true);
            for (String transName : dbConnectionMetadataTransNames) {
                TransMeta trans = customRepository.loadTransformation(transName, dir, null, true, null);
                List<DatabaseMeta> dbs = trans.getDatabases();
                for (DatabaseMeta db : dbs){
                    JSONObject dbObj = new JSONObject();
                    dbObj.put("text",db.getName());
                    dbObj.put("title",db.getName());
                    dbObj.put("icon","/oryx/images/conxbi/etl/connection.gif");
                    dbObj.put("id",db.getObjectId().toString());
                    dbObj.put("leaf",false);
                    dbObj.put("hasChildren",true);
                    dbObj.put("singleClickExpand",true);
                    childrenArray.put(dbObj);

                    //Create tables
                    JSONArray dbObjTableSchemas = new JSONArray();
                    dbObj.put("children",dbObjTableSchemas);
                    JSONObject tableSchemas = new JSONObject();
                    tableSchemas.put("title","Table Schemas");
                    tableSchemas.put("text","Table Schemas");
                    tableSchemas.put("id",transName+".tables.schemas");
                    tableSchemas.put("icon","/oryx/images/conxbi/etl/folder_close.png");
                    tableSchemas.put("leaf",false);
                    tableSchemas.put("hasChildren",true);
                    tableSchemas.put("singleClickExpand",true);


                    Database dbInstance = new Database(db);
                    try {
                        dbInstance.connect();
                    } catch(Exception e) {
                         continue;
                    }
                    Map<String,Collection<String>> tableMap = dbInstance.getTableMap();
                    for (String schemaName : tableMap.keySet())
                        for (String tableName : tableMap.get(schemaName)) {
                            JSONObject schemaObj = new JSONObject();
                            schemaObj.put("id", transName + ".tables.schemas.table." + schemaName + "." + tableName);
                            schemaObj.put("text", schemaName + "." + tableName);
                            schemaObj.put("title", schemaName + "." + tableName);
                            schemaObj.put("icon", "/oryx/images/conxbi/etl/table.gif");
                            schemaObj.put("leaf", false);
                            schemaObj.put("hasChildren", true);
                            schemaObj.put("singleClickExpand", true);

                            JSONArray columns = new JSONArray();
                            schemaObj.put("children",columns);
                            dbObjTableSchemas.put(schemaObj);

                            RowMetaInterface fields = dbInstance.getTableFields(tableName);
                            List<ValueMetaInterface> fvms = fields.getValueMetaList();
                            for (ValueMetaInterface fvm : fvms) {
                                JSONObject column = new JSONObject();
                                String name = fvm.getName();
                                String typeName = fvm.getTypeDesc();
                                column.put("id", schemaObj.get("id")+"."+name);
                                column.put("text", name+"["+typeName+"]");
                                column.put("text", name+"["+typeName+"]");
                                column.put("icon", "/oryx/images/conxbi/etl/columns.gif");
                                column.put("leaf", true);
                                column.put("hasChildren", false);
                                column.put("singleClickExpand", false);
                                columns.put(column);
                            }
                        }
                    dbInstance.disconnect();
                }
            }

            if (childrenArray.length() > 0)
                subDir.put("children",childrenArray);
        } catch (KettleException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return subDir;
    }
}
