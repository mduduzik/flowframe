package org.flowframe.etl.pentaho.server.snaps.core.resource;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.etl.pentaho.server.snaps.core.model.DirectoryDTO;
import org.flowframe.etl.pentaho.server.snaps.core.utils.DatabaseMetaUtil;
import org.flowframe.etl.pentaho.server.snaps.core.utils.RepositoryUtil;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.LongObjectId;
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

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public static String REPOSITORY_ITEM_TYPE = "itemtype";
    public static String REPOSITORY_ITEM_TYPE_DATABASE = "database";
    public static String REPOSITORY_ITEM_TYPE_CSVMETA = "csvmeta";
    public static String REPOSITORY_ITEM_TYPE_EXCELMETA = "excelmeta";
    public static String REPOSITORY_ITEM_PARENTFOLDER_OBJID = "folderObjectId";

    public static String REPOSITORY_ITEMCONTAINER_TYPE = "itemcontainertype";
    public static String REPOSITORY_ITEMCONTAINER_TYPE_REPOFOLDER = "repofolder";
    public static String REPOSITORY_ITEMCONTAINER_TYPE_DYNAMIC = "dynamic";
    public static String REPOSITORY_REPOFOLDER_OBJID = "folderObjectId";

    public static String REPOSITORY_UI_TREE_LOADING_TYPE = "loadingtype";
    public static String REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND = "ondemand";
    public static String REPOSITORY_UI_TREE_LOADING_TYPE_ONTIME = "ontime";
    public static String REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME = "menugroup";
    public static String REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME = "ddenabled";

    @Autowired
    private ICustomRepository repository;

    @POST
    @Path("/adddir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DirectoryDTO adddir(@HeaderParam("userid") String userid, DirectoryDTO record) throws KettleException {
        //TODO: Hack
        Organization tenant = new Organization();
        tenant.setId(1L);

        if (REPOSITORY_ITEM_TYPE_DATABASE.equals(record.getItemtype())) {
            RepositoryDirectoryInterface dir = RepositoryUtil.getDirectory(repository, new LongObjectId(record.getDirObjectId()));
            record = DatabaseMetaUtil.addDatabaseDirectory(repository, dir, tenant, record);
        }

        return record;
    }

    @DELETE
    @Path("/deletedir")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletedir(@HeaderParam("userid") String userid,
                              @HeaderParam("itemtype") String itemtype,
                              @HeaderParam("folderObjectId") String folderObjectId) throws KettleException {
        Organization tenant = new Organization();
        tenant.setId(1L);

        LongObjectId dirObjId = new LongObjectId(Long.valueOf(folderObjectId));
        RepositoryDirectoryInterface dir = RepositoryUtil.getDirectory(repository, dirObjId);

        if (REPOSITORY_ITEM_TYPE_DATABASE.equals(itemtype)) {
            DatabaseMetaUtil.deleteDatabaseDirectory(repository, dir, tenant.getId().toString());
        }
        return Response.ok("Folder " + folderObjectId + " deleted successfully", MediaType.TEXT_PLAIN).build();
    }

    @GET
    @Path("/getall")
    @Produces(MediaType.TEXT_PLAIN)
    public String getall(@QueryParam("userId") String userid, @QueryParam("callback") String callback) throws KettleException, JSONException {
        Organization tenant = new Organization();
        tenant.setId(1L);

        String res = exportTreeToJSONByTenant(null, repository, tenant).toString();

        if (callback != null)
            return callback + "(" + res + ");";
        else
            return res;
    }

    @GET
    @Path("/getnode")
    @Produces(MediaType.TEXT_PLAIN)
    public String getnode(@QueryParam("userid") String userid,
                          @QueryParam("callback") String callback,
                          @QueryParam("itemtype") String itemtype,
                          @QueryParam("node") String nodeId,
                          @QueryParam("folderObjectId") String folderObjectId) throws KettleException, JSONException {
        Organization tenant = new Organization();
        tenant.setId(1L);

        String res = null;
        JSONObject json;
        if (itemtype == null || "undefined".equals(itemtype))
            res = exportTreeToJSONByTenant(null, repository, tenant).toString();
        else if (nodeId != null && nodeId.indexOf("/db/") >= 0){  //DB PathId
            json = generateDBConnectionChildrenJSON(nodeId);
            res = json.getJSONArray("children").toString();
        }
        else {
            json = exportTreeNodeToJSONByTenant(nodeId,itemtype, folderObjectId, repository, tenant, true/*excluderootnode*/, true/*ondemand*/);
            res = json.getJSONArray("children").toString();
        }
        if (callback != null)
            return callback + "(" + res + ");";
        else
            return res;
    }

    private JSONObject exportTreeNodeToJSONByTenant(String nodeId, String itemtype, String folderObjectId, ICustomRepository repository, Organization tenant, boolean excludeRoot, boolean ondemand) throws KettleException, JSONException {
        JSONObject res = null;
        if (REPOSITORY_ITEM_TYPE_DATABASE.equals(itemtype)) {
            LongObjectId dirObjId = new LongObjectId(Long.valueOf(folderObjectId));
            RepositoryDirectoryInterface mdDir = repository.provideMetadataDirectoryForTenant(tenant);
            RepositoryDirectoryInterface dir = mdDir.findDirectory(dirObjId);

            res = generateDBConnectionsJSON(ondemand, false, repository, dir, tenant.getId().toString());
        } else if (REPOSITORY_ITEM_TYPE_CSVMETA.equals(itemtype)) {
            LongObjectId dirObjId = new LongObjectId(Long.valueOf(folderObjectId));
            RepositoryDirectoryInterface mdDir = repository.provideMetadataDirectoryForTenant(tenant);
            RepositoryDirectoryInterface dir = mdDir.findDirectory(dirObjId);

            res = generateCSVMetadataJSON(ondemand, false, repository, dir);
        } else if (REPOSITORY_ITEM_TYPE_EXCELMETA.equals(itemtype)) {
            LongObjectId dirObjId = new LongObjectId(Long.valueOf(folderObjectId));
            RepositoryDirectoryInterface mdDir = repository.provideMetadataDirectoryForTenant(tenant);
            RepositoryDirectoryInterface dir = mdDir.findDirectory(dirObjId);

            res = generateExcelMetadataJSON(ondemand, false, repository, dir);
        }
        return res;
    }



    public static JSONArray exportTreeToJSONByTenant(ProgressMonitorListener monitor, ICustomRepository repo, Organization tenant) throws KettleException, JSONException {
        JSONArray treeByTenant = new JSONArray();

        RepositoryDirectoryInterface mdDir = repo.provideMetadataDirectoryForTenant(tenant);

        RepositoryDirectoryInterface dbConnectionsMdDir = repo.provideDBConnectionsMetadataDirectoryForTenant(tenant);
        RepositoryDirectoryInterface excelMdDir = repo.provideExcelMetadataDirectoryForTenant(tenant);
        RepositoryDirectoryInterface delimitedMdDir = repo.provideDelimitedMetadataDirectoryForTenant(tenant);

        JSONObject metadata = new JSONObject();
        metadata.put("id", "metadata");
        metadata.put("allowDrag", false);
        metadata.put("allowDrop", false);
        metadata.put("text", mdDir.getName());
        metadata.put("title", mdDir.getName());
        metadata.put("icon", "/etl/images/conxbi/etl/package.gif");
        metadata.put("leaf", false);
        metadata.put("hasChildren", true);
        metadata.put("singleClickExpand", true);
        metadata.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONTIME);
        JSONArray metadataChildren = new JSONArray();
        treeByTenant.put(metadata);

            /*
            //-- DB Connections
            */
        //JSONObject metadataDBConnections = generateDBConnectionsJSON(true,false,repo, dbConnectionsMdDir, tenant.getId().toString());
        JSONObject metadataDBConnections = new JSONObject();
        metadataDBConnections.put("id", "metadata.dbconnections");
        metadataDBConnections.put("allowDrag", false);
        metadataDBConnections.put("allowDrop", false);
        metadataDBConnections.put("text", "DB Connections");
        metadataDBConnections.put("title", "DB Connections");
        metadataDBConnections.put("icon", "/etl/images/conxbi/etl/connection.gif");
        metadataDBConnections.put("leaf", false);
        metadataDBConnections.put("hasChildren", true);
        metadataDBConnections.put("singleClickExpand", true);
        metadataDBConnections.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
        metadataDBConnections.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_DATABASE);
        metadataDBConnections.put(REPOSITORY_REPOFOLDER_OBJID, dbConnectionsMdDir.getObjectId().toString());
        metadataDBConnections.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_DATABASE+".folder");

        metadataChildren.put(metadataDBConnections);
        metadata.put("children", metadataChildren);

            /*
            //-- CSV
            */
        //JSONObject metadataDelimited = generateCSVMetadataJSON(true,repo, delimitedMdDir);
        JSONObject metadataDelimited = new JSONObject();
        metadataDelimited.put("id", "metadata.csvmeta");
        metadataDelimited.put("allowDrag", false);
        metadataDelimited.put("allowDrop", false);
        metadataDelimited.put("text", "CSV");
        metadataDelimited.put("title", "CSV");
        metadataDelimited.put("icon", "/etl/images/conxbi/etl/icon_delimited.gif");
        metadataDelimited.put("leaf", false);
        metadataDelimited.put("hasChildren", true);
        metadataDelimited.put("singleClickExpand", true);
        metadataDelimited.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
        metadataDelimited.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_CSVMETA);
        metadataDelimited.put(REPOSITORY_REPOFOLDER_OBJID, delimitedMdDir.getObjectId().toString());
        metadataDelimited.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_CSVMETA+".folder");

        metadataChildren.put(metadataDelimited);


            /*
            //-- Excel
            */
        //JSONObject metadataDExcel = generateExcelMetadataJSON(true, repo, excelMdDir);
        JSONObject metadataDExcel = new JSONObject();
        metadataDExcel.put("id", "metadata.excel");
        metadataDExcel.put("allowDrag", false);
        metadataDExcel.put("allowDrop", false);
        metadataDExcel.put("text", "Excel");
        metadataDExcel.put("title", "Excel");
        metadataDExcel.put("icon", "/etl/images/conxbi/etl/icon_excel.gif");
        metadataDExcel.put("leaf", false);
        metadataDExcel.put("hasChildren", true);
        metadataDExcel.put("singleClickExpand", true);
        metadataDExcel.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
        metadataDExcel.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_EXCELMETA);
        metadataDExcel.put(REPOSITORY_REPOFOLDER_OBJID, excelMdDir.getObjectId().toString());
        metadataDExcel.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_EXCELMETA+".folder");

        metadataChildren.put(metadataDExcel);

        metadata.put("children", metadataChildren);

        return treeByTenant;
    }

    private static JSONObject generateExcelMetadataJSON(Boolean ondemand, boolean excludeChildrenForThisNode, ICustomRepository ICustomRepository, RepositoryDirectoryInterface dir) throws JSONException, KettleException {
        JSONObject subDir = new JSONObject();

        // Populate
        subDir.put("id", RepositoryUtil.generatePathID(dir, "ExcelInput"));
        subDir.put("allowDrag", false);
        subDir.put("allowDrop", false);
        subDir.put("text", dir.getName());
        subDir.put("title", dir.getName());
        subDir.put("icon", "/etl/images/conxbi/etl/icon_excel.gif");
        subDir.put("leaf", false);
        subDir.put("hasChildren", false);
        subDir.put("singleClickExpand", false);
        subDir.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
        subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_EXCELMETA+".folder");

        List<RepositoryDirectoryInterface> excelMdSubDirs = dir.getChildren();
        String[] excelMetadataTransNames = ICustomRepository.getTransformationNames(dir.getObjectId(), true);
        boolean hasChildren = !excelMdSubDirs.isEmpty() || (excelMetadataTransNames.length > 0);
        if (excludeChildrenForThisNode) {
            subDir.put("hasChildren", hasChildren);
            subDir.put("singleClickExpand", hasChildren);
            return subDir;
        }

        JSONArray childrenArray = new JSONArray();
        subDir.put("children", childrenArray);

        //Do sub dirs
        List<RepositoryDirectoryInterface> delimitedMdSubDirs = dir.getChildren();

        for (RepositoryDirectoryInterface subDir_ : delimitedMdSubDirs) {
            JSONObject subDirDir = generateCSVMetadataJSON(ondemand, excludeChildrenForThisNode, ICustomRepository, subDir_);
            childrenArray.put(subDirDir);
        }


        //Do metadata from transformations
        for (String transName : excelMetadataTransNames) {
            TransMeta trans = ICustomRepository.loadTransformation(transName, dir, null, true, null);
            List<StepMeta> steps = trans.getSteps();
            for (StepMeta step : steps) {
                if (step.getTypeId().equalsIgnoreCase("ExcelInput")) {
                    JSONObject mdmObj = new JSONObject();
                    mdmObj.put("text", step.getName());
                    mdmObj.put("title", step.getName());
                    mdmObj.put("icon", "/etl/images/conxbi/etl/icon_excel.gif");
                    mdmObj.put("id", RepositoryUtil.generatePathID(step, steps.indexOf(step)));
                    mdmObj.put("allowDrag", false);
                    mdmObj.put("allowDrop", false);
                    mdmObj.put("leaf", false);
                    mdmObj.put("hasChildren", true);
                    mdmObj.put("singleClickExpand", true);
                    mdmObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                    mdmObj.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_EXCELMETA);
                    mdmObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
                    childrenArray.put(mdmObj);

                    //Create tables
                    JSONArray mdmObjItems = new JSONArray();
                    mdmObj.put("children", mdmObjItems);
                    JSONObject fieldsObj = new JSONObject();
                    fieldsObj.put("title", "Fields");
                    fieldsObj.put("text", "Fields");
                    fieldsObj.put("id", step.getName() + ".fields");
                    fieldsObj.put("allowDrag", false);
                    fieldsObj.put("allowDrop", false);
                    fieldsObj.put("icon", "/etl/images/conxbi/etl/folder_close.png");
                    fieldsObj.put("leaf", false);
                    fieldsObj.put("hasChildren", true);
                    fieldsObj.put("singleClickExpand", true);
                    fieldsObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                    JSONArray fields = new JSONArray();
                    fieldsObj.put("children", fields);
                    mdmObjItems.put(fieldsObj);

                    ExcelInputMeta csvStep = (ExcelInputMeta) step.getStepMetaInterface();
                    ExcelInputField[] inputFields = csvStep.getField();
                    for (ExcelInputField field : inputFields) {
                        JSONObject fieldObj = new JSONObject();
                        fieldObj.put("id", fieldsObj.get("id") + "." + field.getName());
                        fieldObj.put("allowDrag", false);
                        fieldObj.put("allowDrop", false);
                        fieldObj.put("text", field.getName() + "[" + field.getTypeDesc() + "]");
                        fieldObj.put("title", field.getName() + "[" + field.getTypeDesc() + "]");
                        fieldObj.put("icon", "/etl/images/conxbi/etl/columns.gif");
                        fieldObj.put("leaf", true);
                        fieldObj.put("hasChildren", false);
                        fieldObj.put("singleClickExpand", false);
                        fields.put(fieldObj);
                    }
                }
            }
        }

        return subDir;
    }

    private static JSONObject generateCSVMetadataJSON(boolean ondemand, boolean excludeChildrenForThisNode, ICustomRepository ICustomRepository, RepositoryDirectoryInterface dir) throws JSONException, KettleException {
        JSONObject subDir = new JSONObject();

        // Populate
        subDir.put("id", RepositoryUtil.generatePathID(dir, "CSVInput"));
        subDir.put("allowDrag", false);
        subDir.put("allowDrop", false);
        subDir.put("text", dir.getName());
        subDir.put("title", dir.getName());
        subDir.put("icon", "/etl/images/conxbi/etl/icon_delimited.gif");
        subDir.put("leaf", false);
        subDir.put("hasChildren", false);
        subDir.put("singleClickExpand", false);
        subDir.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
        subDir.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_CSVMETA);
        subDir.put(REPOSITORY_ITEMCONTAINER_TYPE, REPOSITORY_ITEMCONTAINER_TYPE_REPOFOLDER);
        subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_CSVMETA+".folder");


        List<RepositoryDirectoryInterface> delimitedMdSubDirs = dir.getChildren();
        String[] dbConnectionMetadataTransNames = ICustomRepository.getTransformationNames(dir.getObjectId(), true);
        boolean hasChildren = !delimitedMdSubDirs.isEmpty() || (dbConnectionMetadataTransNames.length > 0);
        if (excludeChildrenForThisNode) {
            subDir.put("hasChildren", hasChildren);
            subDir.put("singleClickExpand", hasChildren);
            return subDir;
        }

        JSONArray childrenArray = new JSONArray();
        subDir.put("children", childrenArray);


        //Do sub dirs
        for (RepositoryDirectoryInterface subDir_ : delimitedMdSubDirs) {
            JSONObject subDirDir = generateCSVMetadataJSON(ondemand, excludeChildrenForThisNode, ICustomRepository, subDir_);
            childrenArray.put(subDirDir);
        }


        //Do metadata from transformations
        for (String transName : dbConnectionMetadataTransNames) {
            TransMeta trans = ICustomRepository.loadTransformation(transName, dir, null, true, null);
            List<StepMeta> steps = trans.getSteps();
            for (StepMeta step : steps) {
                if (step.getTypeId().equalsIgnoreCase("CsvInput")) {
                    JSONObject mdmObj = new JSONObject();
                    mdmObj.put("text", step.getName());
                    mdmObj.put("title", step.getName());
                    mdmObj.put("icon", "/etl/images/conxbi/etl/icon_delimited.gif");
                    mdmObj.put("id", RepositoryUtil.generatePathID(step, steps.indexOf(step)));
                    mdmObj.put("allowDrag", false);
                    mdmObj.put("allowDrop", false);
                    mdmObj.put("leaf", false);
                    mdmObj.put("hasChildren", true);
                    mdmObj.put("singleClickExpand", true);
                    mdmObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                    mdmObj.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_CSVMETA);
                    mdmObj.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_CSVMETA);
                    mdmObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
                    childrenArray.put(mdmObj);

                    //Create tables
                    JSONArray mdmObjItems = new JSONArray();
                    mdmObj.put("children", mdmObjItems);
                    JSONObject fieldsObj = new JSONObject();
                    fieldsObj.put("title", "Fields");
                    fieldsObj.put("text", "Fields");
                    fieldsObj.put("id", step.getName() + ".fields");
                    fieldsObj.put("allowDrag", false);
                    fieldsObj.put("allowDrop", false);
                    fieldsObj.put("icon", "/etl/images/conxbi/etl/folder_close.png");
                    fieldsObj.put("leaf", false);
                    fieldsObj.put("hasChildren", true);
                    fieldsObj.put("singleClickExpand", true);
                    fieldsObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                    JSONArray fields = new JSONArray();
                    fieldsObj.put("children", fields);
                    mdmObjItems.put(fieldsObj);


                    CsvInputMeta csvStep = (CsvInputMeta) step.getStepMetaInterface();
                    TextFileInputField[] inputFields = csvStep.getInputFields();
                    for (TextFileInputField field : inputFields) {
                        JSONObject fieldObj = new JSONObject();
                        fieldObj.put("id", fieldsObj.get("id") + "." + field.getName());
                        fieldObj.put("allowDrag", false);
                        fieldObj.put("allowDrop", false);
                        fieldObj.put("text", field.getName() + "[" + field.getTypeDesc() + "]");
                        fieldObj.put("title", field.getName() + "[" + field.getTypeDesc() + "]");
                        fieldObj.put("icon", "/etl/images/conxbi/etl/columns.gif");
                        fieldObj.put("leaf", true);
                        fieldObj.put("hasChildren", false);
                        fieldObj.put("singleClickExpand", false);
                        fields.put(fieldObj);
                    }
                }
            }
        }

        subDir.put("children", childrenArray);

        return subDir;
    }

    private static JSONObject generateDBConnectionsJSON(Boolean ondemand, Boolean excludeChildrenForThisNode, ICustomRepository ICustomRepository, RepositoryDirectoryInterface dir, String tenantId) throws JSONException, KettleException {
        boolean hasChildren = false;
        JSONObject subDir = new JSONObject();

        // Populate
        subDir.put("id", "dir/" + dir.getObjectId().toString());
        subDir.put("allowDrag", false);
        subDir.put("allowDrop", false);
        subDir.put("text", dir.getName());
        subDir.put("title", dir.getName());
        subDir.put("icon", "/etl/images/conxbi/etl/folder_close.png");
        subDir.put("leaf", false);
        subDir.put("hasChildren", false);
        subDir.put("singleClickExpand", false);
        subDir.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
        subDir.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_DATABASE);
        subDir.put(REPOSITORY_ITEMCONTAINER_TYPE, REPOSITORY_ITEMCONTAINER_TYPE_REPOFOLDER);
        subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_DATABASE+".folder");

        subDir.put(REPOSITORY_REPOFOLDER_OBJID, dir.getObjectId().toString());

        Collection<DatabaseMeta> dbs = DatabaseMetaUtil.getDatabasesBySubDirAndTenantId(ICustomRepository, dir, tenantId);
        List<RepositoryDirectoryInterface> dbConnectionsMdSubDirs = dir.getChildren();
        hasChildren = !dbConnectionsMdSubDirs.isEmpty() || !dbs.isEmpty();
        if (excludeChildrenForThisNode) {
            subDir.put("hasChildren", hasChildren);
            subDir.put("singleClickExpand", hasChildren);
            return subDir;
        }

        JSONArray childrenArray = new JSONArray();
        subDir.put("children", childrenArray);


        //Do sub dirs
        excludeChildrenForThisNode = !excludeChildrenForThisNode && ondemand;
        for (RepositoryDirectoryInterface subDir_ : dbConnectionsMdSubDirs) {
            JSONObject subDirDir = generateDBConnectionsJSON(ondemand, excludeChildrenForThisNode, ICustomRepository, subDir_, tenantId);
            childrenArray.put(subDirDir);
        }


        //Do actual db connections
        for (DatabaseMeta db : dbs) {
            JSONObject dbObj = new JSONObject();
            dbObj.put("text", db.getName());
            dbObj.put("title", db.getName());
            dbObj.put("icon", "/etl/images/conxbi/etl/connection.gif");
            dbObj.put("id", RepositoryUtil.generatePathID(dir,db));
            dbObj.put("allowDrag", false);
            dbObj.put("allowDrop", false);
            dbObj.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_DATABASE);
            dbObj.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_DATABASE);
            dbObj.put("leaf", false);
            dbObj.put("hasChildren", true);
            dbObj.put("singleClickExpand", true);

            subDir.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
            subDir.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_DATABASE);
            subDir.put(REPOSITORY_REPOFOLDER_OBJID, dir.getObjectId().toString());
            subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME,REPOSITORY_ITEM_TYPE_DATABASE+".folder");
            childrenArray.put(dbObj);

            //Create tables
            JSONArray dbObjTableSchemas = new JSONArray();
            if (!ondemand)
                dbObj.put("children", dbObjTableSchemas);
            JSONObject tableSchemas = new JSONObject();
            tableSchemas.put("title", "Table Schemas");
            tableSchemas.put("text", "Table Schemas");
            tableSchemas.put("id", db.getName() + ".tables.schemas");
            tableSchemas.put("allowDrag", false);
            tableSchemas.put("allowDrop", false);
            tableSchemas.put("icon", "/etl/images/conxbi/etl/folder_close.png");
            tableSchemas.put("leaf", false);
            tableSchemas.put("hasChildren", true);
            tableSchemas.put("singleClickExpand", true);
            tableSchemas.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);


            if (!ondemand) {
                Database dbInstance = new Database(db);
                try {
                    dbInstance.connect();
                } catch (Exception e) {
                    continue;
                }

                Map<String, Collection<String>> tableMap = dbInstance.getTableMap();
                for (String schemaName : tableMap.keySet())
                    for (String tableName : tableMap.get(schemaName)) {
                        JSONObject schemaObj = new JSONObject();
                        schemaObj.put("id", RepositoryUtil.generatePathID(dir,db)+"/"+schemaName + "/" + tableName);
                        schemaObj.put("allowDrag", false);
                        schemaObj.put("allowDrop", false);
                        schemaObj.put("text", schemaName + "." + tableName);
                        schemaObj.put("title", schemaName + "." + tableName);
                        schemaObj.put("icon", "/etl/images/conxbi/etl/table.gif");
                        schemaObj.put("leaf", false);
                        schemaObj.put("hasChildren", true);
                        schemaObj.put("singleClickExpand", true);
                        schemaObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                        schemaObj.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_DATABASE);
                        schemaObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");

                        JSONArray columns = new JSONArray();
                        schemaObj.put("children", columns);
                        dbObjTableSchemas.put(schemaObj);

                        RowMetaInterface fields = dbInstance.getTableFields(tableName);
                        List<ValueMetaInterface> fvms = fields.getValueMetaList();
                        for (ValueMetaInterface fvm : fvms) {
                            JSONObject column = new JSONObject();
                            String name = fvm.getName();
                            String typeName = fvm.getTypeDesc();
                            column.put("id", schemaObj.get("id") + "." + name);
                            column.put("allowDrag", false);
                            column.put("allowDrop", false);
                            column.put("text", name + "[" + typeName + "]");
                            column.put("text", name + "[" + typeName + "]");
                            column.put("icon", "/etl/images/conxbi/etl/columns.gif");
                            column.put("leaf", true);
                            column.put("hasChildren", false);
                            column.put("singleClickExpand", false);
                            columns.put(column);
                        }
                    }
                dbInstance.disconnect();
            }
        }

        return subDir;
    }


    private JSONObject generateDBConnectionChildrenJSON(String pathId) throws KettleException, JSONException {
        ObjectId dbId = RepositoryUtil.getDBObjectIDFromPathID(pathId);
        boolean hasChildren = false;
        JSONObject subDir = new JSONObject();

        DatabaseMeta db = DatabaseMetaUtil.getDatabaseMeta(repository,dbId);
        JSONObject tableSchemas = new JSONObject();

        //Create tables
        JSONArray dbObjTableSchemas = new JSONArray();
        tableSchemas.put("children",dbObjTableSchemas);
        tableSchemas.put("title", "Table Schemas");
        tableSchemas.put("text", "Table Schemas");
        tableSchemas.put("id", db.getName() + ".tables.schemas");
        tableSchemas.put("allowDrag", false);
        tableSchemas.put("allowDrop", false);
        tableSchemas.put("icon", "/etl/images/conxbi/etl/folder_close.png");
        tableSchemas.put("leaf", false);
        tableSchemas.put("hasChildren", true);
        tableSchemas.put("singleClickExpand", true);
        tableSchemas.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);



        final Database dbInstance = new Database(db);
        try {
            dbInstance.connect();
        } catch (Exception e) {
            tableSchemas.put("hasChildren", false);
            tableSchemas.put("singleClickExpand", false);
            return tableSchemas;
        }

        try {
            Map<String, Collection<String>> tableMap = dbInstance.getTableMap();
            for (String schemaName : tableMap.keySet()) {
                for (String tableName : tableMap.get(schemaName)) {
                    JSONObject schemaObj = new JSONObject();
                    schemaObj.put("id", db.getName() + ".tables.schemas.table." + schemaName + "." + tableName);
                    schemaObj.put("allowDrag", false);
                    schemaObj.put("allowDrop", false);
                    schemaObj.put("text", schemaName + "." + tableName);
                    schemaObj.put("title", schemaName + "." + tableName);
                    schemaObj.put("icon", "/etl/images/conxbi/etl/table.gif");
                    schemaObj.put("leaf", false);
                    schemaObj.put("hasChildren", true);
                    schemaObj.put("singleClickExpand", true);
                    schemaObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                    schemaObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
                    schemaObj.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_DATABASE);

                    JSONArray columns = new JSONArray();
                    schemaObj.put("children", columns);
                    dbObjTableSchemas.put(schemaObj);

                    RowMetaInterface fields = dbInstance.getTableFields(tableName);
                    List<ValueMetaInterface> fvms = fields.getValueMetaList();
                    for (ValueMetaInterface fvm : fvms) {
                        JSONObject column = new JSONObject();
                        String name = fvm.getName();
                        String typeName = fvm.getTypeDesc();
                        column.put("id", schemaObj.get("id") + "." + name);
                        column.put("allowDrag", false);
                        column.put("allowDrop", false);
                        column.put("text", name + "[" + typeName + "]");
                        column.put("text", name + "[" + typeName + "]");
                        column.put("icon", "/etl/images/conxbi/etl/columns.gif");
                        column.put("leaf", true);
                        column.put("hasChildren", false);
                        column.put("singleClickExpand", false);
                        columns.put(column);
                    }
                }
            }
        } catch (KettleDatabaseException e) {
            throw e;
        } catch (JSONException e) {
            throw e;
        }
        finally {
           if (dbInstance != null)
               dbInstance.disconnect();
        }

        return tableSchemas;
    }
}