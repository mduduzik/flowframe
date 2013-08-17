package org.flowframe.etl.pentaho.repository.db.resource;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.repository.db.repository.CustomRepository;
import org.flowframe.etl.pentaho.repository.db.repository.DBRepositoryWrapperImpl;
import org.flowframe.etl.pentaho.repository.db.repository.DatabaseMetaUtil;
import org.flowframe.etl.pentaho.repository.db.repository.RepositoryUtil;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.database.util.DatabaseUtil;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;
import org.pentaho.di.trans.steps.excelinput.ExcelInputField;
import org.pentaho.di.trans.steps.excelinput.ExcelInputMeta;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("explorer")
@Component
public class RepositoryExplorerResource {
    @Autowired
    private DBRepositoryWrapperImpl repository;

    @GET
    @Path("/getall")
    @Produces(MediaType.TEXT_PLAIN)
    public String getall(@QueryParam("userId") String userid, @QueryParam("callback") String callback) {
        Organization tenant = new Organization();
        tenant.setId(1L);

        String res = exportTreeToJSONByTenant(null, repository, tenant).toString();

        if (callback != null)
            return callback+"("+res+");";
        else
            return res;
    }

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

            /*
            //-- DB Connections
            */
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

            /*
            //-- CSV
            */
            JSONObject metadataDelimited = generateCSVMetadataJSON(repo, delimitedMdDir);
            metadataDelimited.put("id", RepositoryUtil.generatePathID(delimitedMdDir, "CSVInput"));
            metadataDelimited.put("text","CSV");
            metadataDelimited.put("title","CSV");
            metadataDelimited.put("icon","/oryx/images/conxbi/etl/icon_delimited.gif");
            metadataDelimited.put("leaf",false);
            metadataDelimited.put("hasChildren",true);
            metadataDelimited.put("singleClickExpand",true);

            metadataChildren.put(metadataDelimited);


            /*
            //-- Excel
            */
            JSONObject metadataDExcel = generateExcelMetadataJSON(repo, excelMdDir);
            metadataDExcel.put("id","metadata.excel");
            metadataDExcel.put("text","Excel");
            metadataDExcel.put("title","Excel");
            metadataDExcel.put("icon","/oryx/images/conxbi/etl/icon_excel.gif");
            metadataDExcel.put("leaf",false);
            metadataDExcel.put("hasChildren",true);
            metadataDExcel.put("singleClickExpand",true);

            metadataChildren.put(metadataDExcel);

            metadata.put("children",metadataChildren);

        } catch (KettleException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return treeByTenant;
    }

    private static JSONObject generateExcelMetadataJSON(CustomRepository customRepository, RepositoryDirectoryInterface dir) {
        JSONObject subDir = new JSONObject();
        try {
            // Populate
            subDir.put("id", RepositoryUtil.generatePathID(dir, "ExcelInput"));
            subDir.put("text", dir.getName());
            subDir.put("title",dir.getName());
            subDir.put("icon", "/oryx/images/conxbi/etl/icon_excel.gif");
            subDir.put("leaf",false);
            subDir.put("hasChildren",true);
            subDir.put("singleClickExpand",true);

            JSONArray childrenArray = new JSONArray();

            //Do sub dirs
            List<RepositoryDirectoryInterface> delimitedMdSubDirs = dir.getChildren();

            for (RepositoryDirectoryInterface subDir_ : delimitedMdSubDirs) {
                JSONObject subDirDir = generateCSVMetadataJSON(customRepository, subDir_);
                childrenArray.put(subDirDir);
            }


            //Do metadata from transformations
            String[] dbConnectionMetadataTransNames = customRepository.getTransformationNames(dir.getObjectId(), true);
            for (String transName : dbConnectionMetadataTransNames) {
                TransMeta trans = customRepository.loadTransformation(transName, dir, null, true, null);
                List<StepMeta> steps = trans.getSteps();
                for (StepMeta step : steps){
                    if (step.getTypeId().equalsIgnoreCase("ExcelInput")) {
                        JSONObject mdmObj = new JSONObject();
                        mdmObj.put("text",step.getName());
                        mdmObj.put("title",step.getName());
                        mdmObj.put("icon","/oryx/images/conxbi/etl/icon_excel.gif");
                        mdmObj.put("id", RepositoryUtil.generatePathID(step, steps.indexOf(step)));
                        mdmObj.put("leaf",false);
                        mdmObj.put("hasChildren",true);
                        mdmObj.put("singleClickExpand",true);
                        childrenArray.put(mdmObj);

                        //Create tables
                        JSONArray mdmObjItems = new JSONArray();
                        mdmObj.put("children",mdmObjItems);
                        JSONObject fieldsObj = new JSONObject();
                        fieldsObj.put("title", "Fields");
                        fieldsObj.put("text", "Fields");
                        fieldsObj.put("id", step.getName() + ".fields");
                        fieldsObj.put("icon", "/oryx/images/conxbi/etl/folder_close.png");
                        fieldsObj.put("leaf", false);
                        fieldsObj.put("hasChildren", true);
                        fieldsObj.put("singleClickExpand", true);
                        JSONArray fields = new JSONArray();
                        fieldsObj.put("children", fields);
                        mdmObjItems.put(fieldsObj);

                        ExcelInputMeta csvStep = (ExcelInputMeta)step.getStepMetaInterface();
                        ExcelInputField[] inputFields = csvStep.getField();
                        for (ExcelInputField field : inputFields) {
                            JSONObject fieldObj = new JSONObject();
                            fieldObj.put("id", fieldsObj.get("id")+"."+field.getName());
                            fieldObj.put("text", field.getName()+"["+field.getTypeDesc()+"]");
                            fieldObj.put("title", field.getName()+"["+field.getTypeDesc()+"]");
                            fieldObj.put("icon", "/oryx/images/conxbi/etl/columns.gif");
                            fieldObj.put("leaf", true);
                            fieldObj.put("hasChildren", false);
                            fieldObj.put("singleClickExpand", false);
                            fields.put(fieldObj);
                        }
                    }
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

    private static JSONObject generateCSVMetadataJSON(CustomRepository customRepository, RepositoryDirectoryInterface dir) {
        JSONObject subDir = new JSONObject();
        try {
            // Populate
            subDir.put("id", RepositoryUtil.generatePathID(dir, "CSVInput"));
            subDir.put("text", dir.getName());
            subDir.put("title",dir.getName());
            subDir.put("icon", "/oryx/images/conxbi/etl/icon_delimited.gif");
            subDir.put("leaf",false);
            subDir.put("hasChildren",true);
            subDir.put("singleClickExpand",true);

            JSONArray childrenArray = new JSONArray();

            //Do sub dirs
            List<RepositoryDirectoryInterface> delimitedMdSubDirs = dir.getChildren();

            for (RepositoryDirectoryInterface subDir_ : delimitedMdSubDirs) {
                JSONObject subDirDir = generateCSVMetadataJSON(customRepository, subDir_);
                childrenArray.put(subDirDir);
            }


            //Do metadata from transformations
            String[] dbConnectionMetadataTransNames = customRepository.getTransformationNames(dir.getObjectId(), true);
            for (String transName : dbConnectionMetadataTransNames) {
                TransMeta trans = customRepository.loadTransformation(transName, dir, null, true, null);
                List<StepMeta> steps = trans.getSteps();
                for (StepMeta step : steps){
                    if (step.getTypeId().equalsIgnoreCase("CsvInput")) {
                        JSONObject mdmObj = new JSONObject();
                        mdmObj.put("text",step.getName());
                        mdmObj.put("title",step.getName());
                        mdmObj.put("icon","/oryx/images/conxbi/etl/icon_delimited.gif");
                        mdmObj.put("id", RepositoryUtil.generatePathID(step, steps.indexOf(step)));
                        mdmObj.put("leaf",false);
                        mdmObj.put("hasChildren",true);
                        mdmObj.put("singleClickExpand",true);
                        childrenArray.put(mdmObj);

                        //Create tables
                        JSONArray mdmObjItems = new JSONArray();
                        mdmObj.put("children",mdmObjItems);
                        JSONObject fieldsObj = new JSONObject();
                        fieldsObj.put("title", "Fields");
                        fieldsObj.put("text", "Fields");
                        fieldsObj.put("id", step.getName() + ".fields");
                        fieldsObj.put("icon", "/oryx/images/conxbi/etl/folder_close.png");
                        fieldsObj.put("leaf", false);
                        fieldsObj.put("hasChildren", true);
                        fieldsObj.put("singleClickExpand", true);
                        JSONArray fields = new JSONArray();
                        fieldsObj.put("children", fields);
                        mdmObjItems.put(fieldsObj);


                        CsvInputMeta csvStep = (CsvInputMeta)step.getStepMetaInterface();
                        TextFileInputField[] inputFields = csvStep.getInputFields();
                        for (TextFileInputField field : inputFields) {
                            JSONObject fieldObj = new JSONObject();
                            fieldObj.put("id", fieldsObj.get("id")+"."+field.getName());
                            fieldObj.put("text", field.getName()+"["+field.getTypeDesc()+"]");
                            fieldObj.put("title", field.getName()+"["+field.getTypeDesc()+"]");
                            fieldObj.put("icon", "/oryx/images/conxbi/etl/columns.gif");
                            fieldObj.put("leaf", true);
                            fieldObj.put("hasChildren", false);
                            fieldObj.put("singleClickExpand", false);
                            fields.put(fieldObj);
                        }
                    }
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

    private static JSONObject generateDBConnectionsJSON(CustomRepository customRepository, RepositoryDirectoryInterface dir) {
        JSONObject subDir = new JSONObject();
        try {
            // Populate
            subDir.put("id",dir.getObjectId().toString());
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
            Collection<DatabaseMeta> dbs = DatabaseMetaUtil.getDatabasesByTenant(customRepository,dir,"");
            for (DatabaseMeta db : dbs){
                JSONObject dbObj = new JSONObject();
                dbObj.put("text",db.getName());
                dbObj.put("title",db.getName());
                dbObj.put("icon","/oryx/images/conxbi/etl/connection.gif");
                dbObj.put("id", db.getObjectId());
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
                tableSchemas.put("id",db.getName()+".tables.schemas");
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
                        schemaObj.put("id", db.getName() + ".tables.schemas.table." + schemaName + "." + tableName);
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
