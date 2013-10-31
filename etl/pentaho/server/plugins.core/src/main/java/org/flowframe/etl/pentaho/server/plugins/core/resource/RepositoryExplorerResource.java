package org.flowframe.etl.pentaho.server.plugins.core.resource;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.plugins.core.model.DirectoryDTO;
import org.flowframe.etl.pentaho.server.plugins.core.utils.DatabaseMetaUtil;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entry.JobEntryCopy;
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
    public static String REPOSITORY_ITEM_TYPE_TRANSFORMATION = "transformation";
    public static String REPOSITORY_ITEM_TYPE_JOB = "job";
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

    private ICustomRepository repository;

    @Autowired
    public void setRepository(ICustomRepository repository) {
        this.repository = repository;
    }

    @POST
    @Path("/adddir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DirectoryDTO adddir(@HeaderParam("userid") String userid, DirectoryDTO record) throws KettleException {
        //TODO: Hack
        Organization tenant = new Organization();
        tenant.setId(1L);

        if (record.getDirObjectId() <= 0) {//Root
            LongObjectId objId = null;
            if (REPOSITORY_ITEM_TYPE_TRANSFORMATION.equals(record.getItemtype())) {
                objId = (LongObjectId)repository.provideTransDirectoryForTenant(tenant).getObjectId();
                record.setIcon("/etl/images/conxbi/etl/folder_open_transformations.gif");
            }
            else  if (REPOSITORY_ITEM_TYPE_JOB.equals(record.getItemtype())) {
                objId = (LongObjectId)repository.provideJobsDirectoryForTenant(tenant).getObjectId();
                record.setIcon("/etl/images/conxbi/etl/folder_open_jobs.gif");
            }
            else if (REPOSITORY_ITEM_TYPE_DATABASE.equals(record.getItemtype())) {
                objId = (LongObjectId)repository.provideDBConnectionsMetadataDirectoryForTenant(tenant).getObjectId();
            }
            record.setDirObjectId(objId.longValue());
        }

/*        if (REPOSITORY_ITEM_TYPE_DATABASE.equals(record.getItemtype())) {
            RepositoryDirectoryInterface dir = RepositoryUtil.getDirectory(repository, new LongObjectId(record.getDirObjectId()));
            record = DatabaseMetaUtil.addDatabaseDirectory(repository, dir, tenant, record);
        }
        else if (REPOSITORY_ITEM_TYPE_TRANSFORMATION.equals(record.getItemtype())) {
            if (record.getDirObjectId() < 0) {//Root
                LongObjectId objId = (LongObjectId)repository.provideTransDirectoryForTenant(tenant).getObjectId();
                record.setDirObjectId(objId.longValue());
            }
        }*/
        RepositoryDirectoryInterface dir = RepositoryUtil.getDirectory(repository, new LongObjectId(record.getDirObjectId()));
        RepositoryDirectoryInterface newSubDir = dir.findDirectory(record.getName());
        if (newSubDir != null) {
            record.setRequestMsg("Directory "+record.getName()+" already exists. Use a different name");
        }


        //Create sub-dir
         newSubDir = repository.createRepositoryDirectory(dir, record.getName());
        repository.getRepositoryConnectionDelegate().commit();
        record.setDirObjectId(((LongObjectId)newSubDir.getObjectId()).longValue());


        return record;
    }

    @POST
    @Path("/deldir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletedir(@HeaderParam("userid") String userid, DirectoryDTO record) throws KettleException {
        Organization tenant = new Organization();
        tenant.setId(1L);

        LongObjectId dirObjId = new LongObjectId(Long.valueOf(record.getDirObjectId()));
        RepositoryDirectoryInterface dir = RepositoryUtil.getDirectory(repository, dirObjId);

        if (REPOSITORY_ITEM_TYPE_DATABASE.equals(record.getItemtype())) {
            DatabaseMetaUtil.deleteDatabaseDirectory(repository, dir, tenant.getId().toString());
        }
        else {
            repository.deleteRepositoryDirectory(dir);
        }
        return Response.ok("Folder " + dir.getName() + " deleted successfully", MediaType.TEXT_PLAIN).build();
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

        //Transformations node
        if (nodeId != null && "transformations".equals(nodeId)) {
            json = generateTransformationsJsonTreeData(true, true, repository, tenant);
            res = json.getJSONArray("children").toString();
        }
        else if (nodeId != null && "transformation".equals(itemtype)) {
            RepositoryDirectoryInterface nodeDir = RepositoryUtil.getDirectory(repository, nodeId);
            json = generateTransformationsRecursiveSubTreeData(true, true, repository, nodeDir);
            res = json.getJSONArray("children").toString();
        }
        //Job node
        else if (nodeId != null && "jobs".equals(nodeId)) {
            json = generateJobsJsonTreeData(true, true, repository, tenant);
            res = json.getJSONArray("children").toString();
        }
        else if (nodeId != null && "job".equals(itemtype)) {
            RepositoryDirectoryInterface nodeDir = RepositoryUtil.getDirectory(repository, nodeId);
            json = generateJobsRecursiveSubTreeData(true, true, repository, nodeDir);
            res = json.getJSONArray("children").toString();
        }
        //Root
        else if (itemtype == null || "undefined".equals(itemtype)) {
            res = exportTreeToJSONByTenant(null, repository, tenant).toString();
        }
        //Metadata nodes
        else if (nodeId != null && nodeId.indexOf("/db/") >= 0) {  //DB PathId
            json = generateDBConnectionChildrenJSON(nodeId);
            res = json.getJSONArray("children").toString();
        } else {
            json = exportTreeNodeToJSONByTenant(nodeId, itemtype, folderObjectId, repository, tenant, true/*excluderootnode*/, true/*ondemand*/);
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

        //-- Metadata
        generateMetadataSubTreeData(repo, tenant, treeByTenant);

        //-- Transformations
        JSONObject transNode = generateTransformationsJsonTreeData(true, true, repo, tenant);
        treeByTenant.put(transNode);

        //-- Jobs
        JSONObject jobNode = generateJobsJsonTreeData(true, true, repo, tenant);
        treeByTenant.put(jobNode);

        return treeByTenant;
    }

    //{{Transformations
    private static JSONObject generateTransformationsJsonTreeData(Boolean ondemand, boolean excludeChildrenForThisNode, ICustomRepository repo, Organization tenant) throws KettleException, JSONException {
        RepositoryDirectoryInterface transDir = repo.provideTransDirectoryForTenant(tenant);


        JSONObject transformations = new JSONObject();
        transformations.put("id", "transformations");
        transformations.put("allowDrag", false);
        transformations.put("allowDrop", false);
        transformations.put("text", transDir.getName());
        transformations.put("title", transDir.getName());
        transformations.put("icon", "/etl/images/conxbi/etl/folder_open_transformations.gif");
        transformations.put("leaf", false);
        transformations.put("hasChildren", true);
        transformations.put("singleClickExpand", true);
        transformations.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_TRANSFORMATION);
        transformations.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONTIME);
        transformations.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_TRANSFORMATION + ".folder");

        JSONArray transChildren = new JSONArray();
        transformations.put("children", transChildren);
        //Do sub dirs
        List<RepositoryDirectoryInterface> transSubDirs = transDir.getChildren();

        for (RepositoryDirectoryInterface subDir_ : transSubDirs) {
            JSONObject subDirDir = generateTransformationsRecursiveSubTreeData(ondemand, excludeChildrenForThisNode, repo, subDir_);
            transChildren.put(subDirDir);
        }

        //Process trans transformations
        generateJsonTreeFromTransformationArtifacts(false/*always include leaves for subdir*/, repo, transDir, transformations, transSubDirs, transChildren);


        return transformations;
    }

    private static JSONObject generateTransformationsRecursiveSubTreeData(Boolean ondemand, boolean excludeChildrenForThisNode, ICustomRepository ICustomRepository, RepositoryDirectoryInterface dir) throws JSONException, KettleException {
        JSONObject subDir = new JSONObject();

        // Populate
        subDir.put("id", RepositoryUtil.generatePathID(dir));
        subDir.put("allowDrag", false);
        subDir.put("allowDrop", false);
        subDir.put("text", dir.getName());
        subDir.put("title", dir.getName());
        subDir.put("icon", "/etl/images/conxbi/etl/folder_open_transformations.gif");
        subDir.put("leaf", false);
        subDir.put("hasChildren", false);
        subDir.put("singleClickExpand", false);
        subDir.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_TRANSFORMATION);
        subDir.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
        subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_TRANSFORMATION + ".folder");

        List<RepositoryDirectoryInterface> subDirs = dir.getChildren();


        JSONArray childrenArray = new JSONArray();
        subDir.put("children", childrenArray);

        //Do sub dirs
        for (RepositoryDirectoryInterface subDir_ : subDirs) {
            JSONObject subDirDir = generateTransformationsRecursiveSubTreeData(ondemand, excludeChildrenForThisNode, ICustomRepository, subDir_);
            childrenArray.put(subDirDir);
        }


        //Process trans transformations
        generateJsonTreeFromTransformationArtifacts(false/*always include leaves for subdir*/, ICustomRepository, dir, subDir, subDirs, childrenArray);

        return subDir;
    }

    private static boolean generateJsonTreeFromTransformationArtifacts(boolean excludeChildrenForThisNode, ICustomRepository ICustomRepository, RepositoryDirectoryInterface dir, JSONObject subDir, List<RepositoryDirectoryInterface> subDirs, JSONArray childrenArray) throws KettleException, JSONException {
        String[] transNames = ICustomRepository.getTransformationNames(dir.getObjectId(), true);
        boolean hasChildren = !subDirs.isEmpty() || (transNames.length > 0);
        if (excludeChildrenForThisNode) {
            subDir.put("hasChildren", hasChildren);
            subDir.put("singleClickExpand", hasChildren);
            return true;
        }

        if (transNames.length > 0) {
            for (String transName : transNames) {
                TransMeta trans = ICustomRepository.loadTransformation(transName, dir, null, true, null);

                //-- Transformation
                JSONObject transObj = new JSONObject();
                transObj.put("text", trans.getName());
                transObj.put("title", trans.getName());
                transObj.put("icon", "/etl/images/conxbi/etl/transformation.png");
                transObj.put("id", trans.getObjectId().toString());
                transObj.put("allowDrag", false);
                transObj.put("allowDrop", false);
                transObj.put("leaf", false);
                transObj.put("hasChildren", true);
                transObj.put("singleClickExpand", true);
                transObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                transObj.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_TRANSFORMATION);
                transObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
                childrenArray.put(transObj);

                //-- Steps
                JSONArray transChildren = new JSONArray();
                transObj.put("children", transChildren);

                JSONObject stepsFolderObj = new JSONObject();
                stepsFolderObj.put("text", "Steps");
                stepsFolderObj.put("title", "Steps");
                stepsFolderObj.put("icon", "/etl/images/conxbi/etl/folder_open_transformations.gif");
                stepsFolderObj.put("id", trans.getObjectId()+"-Steps");
                stepsFolderObj.put("allowDrag", false);
                stepsFolderObj.put("allowDrop", false);
                stepsFolderObj.put("leaf", false);
                stepsFolderObj.put("hasChildren", true);
                stepsFolderObj.put("singleClickExpand", true);
                stepsFolderObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                stepsFolderObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "false");
                transChildren.put(stepsFolderObj);

                List<StepMeta> steps = trans.getSteps();
                for (StepMeta step : steps) {
                    JSONArray stepsFolderChildren = null;
                    if (!stepsFolderObj.has("children")) {
                        stepsFolderChildren = new JSONArray();
                        stepsFolderObj.put("children", stepsFolderChildren);
                    }
                    else
                        stepsFolderChildren = (JSONArray)stepsFolderObj.get("children");

                    JSONObject stepObj = new JSONObject();
                    stepObj.put("text", step.getName());
                    stepObj.put("title", step.getName());
                    stepObj.put("icon", "/etl/images/conxbi/etl/transformation_plugin.png");
                    stepObj.put("id", RepositoryUtil.generatePathID(step, steps.indexOf(step)));
                    stepObj.put("allowDrag", false);
                    stepObj.put("allowDrop", false);
                    stepObj.put("leaf", false);
                    stepObj.put("hasChildren", true);
                    stepObj.put("singleClickExpand", true);
                    stepObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                    stepObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
                    stepsFolderChildren.put(stepObj);

                    //Create fields
                    JSONArray stepObjItems = new JSONArray();
                    stepObj.put("children", stepObjItems);
                    JSONObject fieldsObj = new JSONObject();
                    fieldsObj.put("title", "Fields");
                    fieldsObj.put("text", "Fields");
                    fieldsObj.put("id", step.getName() + ".fields");
                    fieldsObj.put("allowDrag", false);
                    fieldsObj.put("allowDrop", false);
                    fieldsObj.put("icon", "/etl/images/conxbi/etl/folder_open_transformations.gif");
                    fieldsObj.put("leaf", false);
                    fieldsObj.put("hasChildren", false);
                    fieldsObj.put("singleClickExpand", false);
                    fieldsObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                    stepObjItems.put(fieldsObj);


                    JSONArray fields_ = null;
                    if (step.getStepMetaInterface() instanceof ExcelInputMeta) {
                        if (!fieldsObj.has("children")) {
                            fields_ = new JSONArray();
                            fieldsObj.put("children", fields_);
                        }
                        else
                            fields_ = (JSONArray)fieldsObj.get("children");

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
                            fields_.put(fieldObj);
                        }
                    } else if (step.getStepMetaInterface() instanceof CsvInputMeta) {
                        if (!fieldsObj.has("children")) {
                            fields_ = new JSONArray();
                            fieldsObj.put("children", fields_);
                        }
                        else
                            fields_ = (JSONArray)fieldsObj.get("children");

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
                            fields_.put(fieldObj);
                        }
                    }
                }
            }
        }
        return false;
    }
    //}}Transformations

    //{{Jobs
    private static JSONObject generateJobsJsonTreeData(Boolean ondemand, boolean excludeChildrenForThisNode, ICustomRepository repo, Organization tenant) throws KettleException, JSONException {
        RepositoryDirectoryInterface transDir = repo.provideJobsDirectoryForTenant(tenant);


        JSONObject jobs = new JSONObject();
        jobs.put("id", "jobs");
        jobs.put("allowDrag", false);
        jobs.put("allowDrop", false);
        jobs.put("text", transDir.getName());
        jobs.put("title", transDir.getName());
        jobs.put("icon", "/etl/images/conxbi/etl/folder_open_jobs.gif");
        jobs.put("leaf", false);
        jobs.put("hasChildren", true);
        jobs.put("singleClickExpand", true);
        jobs.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_JOB);
        jobs.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONTIME);
        jobs.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_JOB + ".folder");

        JSONArray transChildren = new JSONArray();
        jobs.put("children", transChildren);
        //Do sub dirs
        List<RepositoryDirectoryInterface> transSubDirs = transDir.getChildren();

        for (RepositoryDirectoryInterface subDir_ : transSubDirs) {
            JSONObject subDirDir = generateJobsRecursiveSubTreeData(ondemand, excludeChildrenForThisNode, repo, subDir_);
            transChildren.put(subDirDir);
        }

        return jobs;
    }

    private static JSONObject generateJobsRecursiveSubTreeData(Boolean ondemand, boolean excludeChildrenForThisNode, ICustomRepository ICustomRepository, RepositoryDirectoryInterface dir) throws JSONException, KettleException {
        JSONObject subDir = new JSONObject();

        // Populate
        subDir.put("id", "dir/" + dir.getObjectId().toString());
        subDir.put("allowDrag", false);
        subDir.put("allowDrop", false);
        subDir.put("text", dir.getName());
        subDir.put("title", dir.getName());
        subDir.put("icon", "/etl/images/conxbi/etl/folder_open_jobs.gif");
        subDir.put("leaf", false);
        subDir.put("hasChildren", false);
        subDir.put("singleClickExpand", false);
        subDir.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_TRANSFORMATION);
        subDir.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
        subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_JOB+".folder");

        List<RepositoryDirectoryInterface> subDirs = dir.getChildren();


        JSONArray childrenArray = new JSONArray();
        subDir.put("children", childrenArray);

        //Do sub dirs
        for (RepositoryDirectoryInterface subDir_ : subDirs) {
            JSONObject subDirDir = generateJobsRecursiveSubTreeData(ondemand, excludeChildrenForThisNode, ICustomRepository, subDir_);
            childrenArray.put(subDirDir);
        }


        //Process trans transformations
        generateJsonTreeFromJobArtifacts(false/*always include leaves for subdir*/, ICustomRepository, dir, subDir, subDirs, childrenArray);

        return subDir;
    }

    private static boolean generateJsonTreeFromJobArtifacts(boolean excludeChildrenForThisNode, ICustomRepository ICustomRepository, RepositoryDirectoryInterface dir, JSONObject subDir, List<RepositoryDirectoryInterface> subDirs, JSONArray childrenArray) throws KettleException, JSONException {
        String[] jobNames = ICustomRepository.getJobNames(dir.getObjectId(), true);
        boolean hasChildren = !subDirs.isEmpty() || (jobNames.length > 0);
        if (excludeChildrenForThisNode) {
            subDir.put("hasChildren", hasChildren);
            subDir.put("singleClickExpand", hasChildren);
            return true;
        }

        if (jobNames.length > 0) {
            for (String jobName : jobNames) {
                JobMeta job = ICustomRepository.loadJob(jobName, dir, null, null);

                //-- Job
                JSONObject transObj = new JSONObject();
                transObj.put("text", job.getName());
                transObj.put("title", job.getName());
                transObj.put("icon", "/etl/images/conxbi/etl/process_icon.gif");
                transObj.put("id", job.getObjectId().toString());
                transObj.put("allowDrag", false);
                transObj.put("allowDrop", false);
                transObj.put("leaf", false);
                transObj.put("hasChildren", true);
                transObj.put("singleClickExpand", true);
                transObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                transObj.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_JOB);
                transObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
                childrenArray.put(transObj);

                //-- Entries
                JSONArray transChildren = new JSONArray();
                transObj.put("children", transChildren);

                JSONObject jobEntriesFolderObj = new JSONObject();
                jobEntriesFolderObj.put("text", "Entries");
                jobEntriesFolderObj.put("title", "Entries");
                jobEntriesFolderObj.put("icon", "/etl/images/conxbi/etl/folder_open_jobs.gif");
                jobEntriesFolderObj.put("id", job.getObjectId().toString());
                jobEntriesFolderObj.put("allowDrag", false);
                jobEntriesFolderObj.put("allowDrop", false);
                jobEntriesFolderObj.put("leaf", false);
                jobEntriesFolderObj.put("hasChildren", true);
                jobEntriesFolderObj.put("singleClickExpand", true);
                jobEntriesFolderObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                jobEntriesFolderObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "false");
                transChildren.put(jobEntriesFolderObj);

                List<JobEntryCopy> jobEntries = job.getJobCopies();
                for (JobEntryCopy entry : jobEntries) {
                    JSONArray fields = null;
                    if (!jobEntriesFolderObj.has("children")) {
                        fields = new JSONArray();
                        jobEntriesFolderObj.put("children", fields);
                    }
                    else
                        fields = (JSONArray)jobEntriesFolderObj.get("children");

                    JSONObject stepObj = new JSONObject();
                    stepObj.put("text", entry.getName());
                    stepObj.put("title", entry.getName());
                    stepObj.put("icon", "/etl/images/conxbi/etl/process_plugin.gif");
                    stepObj.put("id", RepositoryUtil.generatePathID(entry, jobEntries.indexOf(entry)));
                    stepObj.put("allowDrag", false);
                    stepObj.put("allowDrop", false);
                    stepObj.put("leaf", true);
                    stepObj.put("hasChildren", false);
                    stepObj.put("singleClickExpand", false);
                    stepObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                    stepObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
                    fields.put(stepObj);
                }
            }
        }
        return false;
    }
    //}}Jobs

    private static void generateMetadataSubTreeData(ICustomRepository repo, Organization tenant, JSONArray treeByTenant) throws KettleException, JSONException {
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
        metadataDBConnections.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_DATABASE + ".folder");

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
        metadataDelimited.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_CSVMETA + ".folder");

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
        metadataDExcel.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_EXCELMETA + ".folder");

        metadataChildren.put(metadataDExcel);

        metadata.put("children", metadataChildren);
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
        subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_EXCELMETA + ".folder");

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
                    mdmObj.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_EXCELMETA);
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
        subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_CSVMETA + ".folder");


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
                    mdmObj.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_CSVMETA);
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
        subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_DATABASE + ".folder");

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
            dbObj.put("id", RepositoryUtil.generatePathID(dir, db));
            dbObj.put("allowDrag", false);
            dbObj.put("allowDrop", false);
            dbObj.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_DATABASE);
            dbObj.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_DATABASE);
            dbObj.put("leaf", false);
            dbObj.put("hasChildren", true);
            dbObj.put("singleClickExpand", true);

            subDir.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
            subDir.put(REPOSITORY_ITEM_TYPE, REPOSITORY_ITEM_TYPE_DATABASE);
            subDir.put(REPOSITORY_REPOFOLDER_OBJID, dir.getObjectId().toString());
            subDir.put(REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME, REPOSITORY_ITEM_TYPE_DATABASE + ".folder");
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
                        schemaObj.put("id", RepositoryUtil.generatePathID(dir, db) + "/" + schemaName + "/" + tableName);
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

        DatabaseMeta db = DatabaseMetaUtil.getDatabaseMeta(repository, dbId);
        JSONObject tableSchemas = new JSONObject();

        //Create tables
        JSONArray dbObjTableSchemas = new JSONArray();
        tableSchemas.put("children", dbObjTableSchemas);
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
                schemaName = (schemaName != null ? schemaName.trim() : "");
                for (String tableName : tableMap.get(schemaName)) {
                    JSONObject schemaObj = new JSONObject();
                    schemaObj.put("id", pathId + "/schema/" + schemaName + "/table/" + tableName);
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
        } finally {
            if (dbInstance != null)
                dbInstance.disconnect();
        }

        return tableSchemas;
    }
}
